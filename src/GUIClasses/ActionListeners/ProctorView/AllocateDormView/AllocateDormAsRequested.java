package GUIClasses.ActionListeners.ProctorView.AllocateDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.Request;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.DormitoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AllocateDormAsRequested implements ActionListener {
    private DormitoryView parentComponent;
    private ArrayList<Dormitory> availableDorms;
    private HashMap<String, Student> students;
    private ArrayList<Integer> requests;
    private ArrayList<String> reporterIds;
    private int remainingStudents; //The remaining students after the allocation.
    public AllocateDormAsRequested(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
        availableDorms = new ArrayList<>();
        students = new HashMap<>();
        reporterIds = new ArrayList<>();
        requests = new ArrayList<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean updateStatus = false;

        String query = "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Allocate Dorm', '"+
                Request.getCurrentDate()+"' , '"+parentComponent.getBuildingNo()+"')";

        loadAvailableDorms();
        loadReporterAndReportId();
        loadStudents();
        sortDormOnAvailableSpace();
        sortDormOnBuildingNo();
        updateStatus = allocateStudents();
        insertHistory(query);
        updateRequestStatus();
        displayUpdateStatus(updateStatus);
    }

    public void loadAvailableDorms(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM AvailableDorm";
        ResultSet resultSet = javaConnection.selectQuery(query);
        try{
            while(resultSet.next()){
                JavaConnection javaConnection1 = new JavaConnection(JavaConnection.URL);
                String roomNo = resultSet.getString("roomNumber");
                String buildingNumber = resultSet.getString("buildingNumber");
                int maxCapacity = resultSet.getInt("maxCapacity");
                Dormitory tmp = new Dormitory(roomNo,buildingNumber,maxCapacity);

                String query2 = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT " +
                        "WHERE BuildingNumber='"+buildingNumber+"' AND RoomNumber='"+roomNo+"'";
                ResultSet rs = javaConnection1.selectQuery(query2);

                while(rs.next()){
                    tmp.setNoOfStudents(rs.getInt("numberOfStudents"));
                }
                availableDorms.add(tmp);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            //Leave the implementation of this block.
        }
    }

    public void loadReporterAndReportId(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT ReportId, ReporterId FROM dormRequests ORDER BY ReportedDate ASC";//Gives priority to the reported date.
        ResultSet resultSet;
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    reporterIds.add(resultSet.getString("ReporterId"));
                    requests.add(resultSet.getInt("ReportId"));
                }

            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
    }

    public void loadStudents(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet;
        if(javaConnection.isConnected()){
            for(String SID: reporterIds){
                Student tmp;
                String query = "SELECT * FROM Student WHERE SID='"+SID+"'";
                resultSet = javaConnection.selectQuery(query);
                try{
                    resultSet.next();
                    tmp = new Student(
                            resultSet.getString("Fname"),
                            resultSet.getString("Lname"),
                            SID,
                            resultSet.getString("Gender")
                    );
                    students.put(SID,tmp);
                } catch (SQLException ex){
                    ex.printStackTrace();//For debugging only.
                }
            }
        }
    }
    public void sortDormOnBuildingNo(){
        Dormitory tmp;
        for(int i = 0; i<availableDorms.size(); i++){
            for(int j = 0; j<availableDorms.size(); j++){
                boolean isFoundInProctorsBuilding = availableDorms.get(j).getBuildingNo()
                        .equals(parentComponent.getProctor().getBuildingNo());
                if(isFoundInProctorsBuilding){
                    /*
                    The following code will sort the dorms by giving priority to the
                    dorms that are found in the proctor's building.
                    */
                    tmp = availableDorms.get(j);
                    availableDorms.set(j,availableDorms.get(0));
                    availableDorms.set(0,tmp);
                }
            }
        }
    }

    public void sortDormOnAvailableSpace(){
        for(int i = 0; i<availableDorms.size(); i++){
            for(int j = 0; j<availableDorms.size(); j++){
                Dormitory tmp;
                try{
                    int availableSpace1 = availableDorms.get(j).getMaxCapacity()-availableDorms.get(j).getNoOfStudents();
                    int availableSpace2 = availableDorms.get(j+1).getMaxCapacity()-availableDorms.get(j+1).getNoOfStudents();
                    if( availableSpace1 < availableSpace2 ){
                        tmp = availableDorms.get(j+1);
                        availableDorms.set(j+1,availableDorms.get(j));
                        availableDorms.set(j,tmp);
                    }
                } catch (IndexOutOfBoundsException ex){
                    //No need to implement this block.
                }
            }
        }
    }

    public int getAvailableSpaces(){
            int totalSpace = 0;
            for(Dormitory dormitory: availableDorms){
                totalSpace += dormitory.getMaxCapacity() - dormitory.getNoOfStudents();
            }
            return totalSpace;
    }

    public boolean allocateStudents(){
        boolean updateStatus = false;
        int totalSpace = getAvailableSpaces();
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "";
        for(int i = 0; i<reporterIds.size(); i++){
            Dormitory tmpDorm = availableDorms.get(i);
            String SID = reporterIds.get(i);
            students.get(SID).setBuildingNo(tmpDorm.getBuildingNo());
            students.get(SID).setDormNo(tmpDorm.getRoomNO());
        }

        if(javaConnection.isConnected()){
            for(Student student: students.values()){
                query = "UPDATE Student SET BuildingNumber='"+student.getBuildingNo() +
                        "', RoomNumber='"+student.getDormNo()+
                        "' WHERE SID='"+student.getsId()+"'";
                updateStatus = javaConnection.updateQuery(query);
            }
            updateStatus &= (totalSpace >= reporterIds.size());
            remainingStudents = reporterIds.size()-totalSpace;
            if(remainingStudents<0) remainingStudents = 0;
        }
        return updateStatus;
    }

    public void updateRequestStatus(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        int size = requests.size()-remainingStudents; //To prevent setting the status of the unhandled reports.
        for(int i = 0; i< size; i++){
            try{
                String query = "INSERT INTO ProctorHandlesReport(EID,ReportId,handledDate) " +
                        "VALUES('"+parentComponent.getProctor().getpId()+"', "+
                        requests.get(i)+", '"+ Request.getCurrentDate()+"')";
                if(javaConnection.isConnected()){
                    javaConnection.insertQuery(query);
                }
            }catch (IndexOutOfBoundsException ex){
                //No need to implement this code.
            }

        }
    }

    public void displayUpdateStatus(boolean updateStatus){
        if(updateStatus)
            JOptionPane.showMessageDialog(parentComponent,"Allocation Successful.");
        else{
            if(remainingStudents == 0)
                JOptionPane.showMessageDialog(parentComponent,"No students left to allocate.");
            else
                JOptionPane.showMessageDialog(parentComponent,"Couldn't allocate "+ remainingStudents+" students due to some problem.\n " +
                        "Make sure there is available space and also the destination exits.");
        }
    }
    public void insertHistory(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        if(javaConnection.isConnected()){
            javaConnection.insertQuery(query);
        }
    }
}
