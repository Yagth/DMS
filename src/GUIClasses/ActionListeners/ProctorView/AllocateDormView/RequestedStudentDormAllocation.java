package GUIClasses.ActionListeners.ProctorView.AllocateDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.Request;
import GUIClasses.ProctorViews.AllocationForm;
import GUIClasses.ProctorViews.DormitoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestedStudentDormAllocation extends NewStudentsDormAllocation {
    protected ArrayList<Integer> requests;
    protected ArrayList<String> reporterIds;
    public RequestedStudentDormAllocation(DormitoryView parentComponent){
        super(parentComponent);
        reporterIds = new ArrayList<>();
        requests = new ArrayList<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int choice = 0;
        boolean updateStatus = false;
        String query2 = "SELECT COUNT(*) AS TotalNo FROM AvailableDorm";
        loadAndSetTotalPage(query2);
        resetPageNumber();

        choice = JOptionPane.showConfirmDialog(parentComponent,"Do you want to allocate students automatically?",
                "Automatic allocation confirmation",JOptionPane.YES_NO_CANCEL_OPTION);
        switch (choice){
            case 0:
                students.clear();
                updateStatus = allocateAllStudents();

                String query= "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                        " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Allocate Dorm', '"+
                        Request.getCurrentDate()+"' , '"+parentComponent.getProctor().getBuildingNo()+"')";

                insertHistory(query);
                displayUpdateStatus(updateStatus);
                break;
            case 1:
                parentComponent.setVisible(false);
                new AllocationForm(parentComponent.getProctor(),parentComponent);
                break;
            case 2:
                return;
        }
    }

    @Override
    public boolean allocateAllStudents(){
        boolean updateStatus;
        do{
            loadAvailableDorms();
            sortDormOnBuildingNo();
            totalSpace = getTotalSpace();
            loadReport();
            loadLocalStudents();
            remainingStudents = students.size();
            updateStatus = allocate();
            incrementPageNumber();
            if(getPageNumber()>getTotalPage()) resetPageNumber();
            updateRequestStatus();
            updateDormInfo();
        } while(remainingStudents>0);
        return updateStatus;
    }

    public void loadLocalStudents(){
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

    public void loadReport(){
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
}
