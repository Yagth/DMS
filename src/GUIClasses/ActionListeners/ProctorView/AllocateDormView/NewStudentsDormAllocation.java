package GUIClasses.ActionListeners.ProctorView.AllocateDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Others.LoadingThread;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.Request;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.LoginPage;
import GUIClasses.ProctorViews.DormitoryView;
import GUIClasses.ProctorViews.LoadingClass;
import GUIClasses.TableViewPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NewStudentsDormAllocation extends TableViewPage implements ActionListener {
    protected DormitoryView parentComponent;
    protected ArrayList<Dormitory> availableDorms;
    protected HashMap<String, Student> students;
    protected int remainingStudents; //The remaining students after the allocation.
    protected int totalSpace;
    protected LoadingClass loadingClass;
    public NewStudentsDormAllocation(DormitoryView parentComponent){
        this.parentComponent = parentComponent;

        availableDorms = new ArrayList<>();
        students = new HashMap<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean updateStatus;
        String query;
        String query2 = "SELECT COUNT(*) AS TotalNo FROM AvailableDorm";
        parentComponent.setVisible(false);

        loadAndSetTotalPage(query2);
        resetPageNumber();
        students.clear();
        updateStatus = allocateAllStudents();

        query = "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Allocate Dorm', '"+
                Request.getCurrentDate()+"' , '"+parentComponent.getProctor().getBuildingNo()+"')";

        insertHistory(query);
        displayUpdateStatus(updateStatus);
        parentComponent.setVisible(true);
    }

    public boolean allocateAllStudents(){
        String query;
        boolean updateStatus;
        query = "SELECT COUNT(*) AS TotalNo FROM STUDENT WHERE BuildingNumber IS NULL AND RoomNumber IS NULL " +
                "AND Place != 'ADDIS ABABA' AND isEligible = 1 ";
        remainingStudents = getTotalStudentNo(query);
        do{

            loadAvailableDorms();
            sortDormOnBuildingNo();
            totalSpace = getTotalSpace();

            System.out.println("TotalSpace: "+totalSpace);

            loadNewStudents();
            updateStatus = allocate();
            incrementPageNumber();
            if(getPageNumber()>getTotalPage()) resetPageNumber();
            updateDormInfo();
        }while(remainingStudents>0);
        return updateStatus;
    }
    public boolean allocate(){
        boolean updateStatus = false;
        int choice = 0;
        if(totalSpace<remainingStudents) {
            choice = JOptionPane.showConfirmDialog(parentComponent,"There is not enough space for all students.\nDo you still want to continue?");
            if(choice == 1) return false;
        } else if(remainingStudents == 0){
            JOptionPane.showMessageDialog(parentComponent,"No students left to allocate.");
            return false;
        } else{
            int count = 0;
            //To update the students stored on the memory.
            for(Student student : students.values()){
                if(count<availableDorms.size()){
                    Dormitory dorm = availableDorms.get(count);
                    boolean hasLockers = dorm.getNoOfLockers()>dorm.getNoOfStudents();
                    boolean isRightGender = student.getGender().equalsIgnoreCase(dorm.getDormType());
                    boolean isFirstStudent = (dorm.getNoOfStudents() == 0);

                    System.out.println("Is first Student: "+isFirstStudent);
                    System.out.println("SID: "+student.getsId());

                    if(hasLockers & isRightGender){
                            student.setBuildingNo(dorm.getBuildingNo());
                            student.setDormNo(dorm.getRoomNO());
                            if(isFirstStudent){
                                dorm.setKeyHolderId(student.getsId());
                            }
                            count++;
                    }
                }
            }

            //To update the database accordingly.
            Iterator it = students.entrySet().iterator();
            while(it.hasNext()){
                Student student;
                Map.Entry tmp= (Map.Entry) it.next();
                student =(Student) tmp.getValue();

                JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
                String query = "";
                boolean hasDorm = student.getDormNo() != 0 & student.getBuildingNo() != 0;

                if(hasDorm){
                    query = "UPDATE Student SET BuildingNumber = '"+student.getBuildingNo()+
                            "', RoomNumber = '"+student.getDormNo()+"', pillow=1,matress=1,bedBase=1 WHERE SID = '"+student.getsId()+"' ";
                    updateStatus = javaConnection.updateQuery(query);

                    System.out.println("Query: "+query);

                    query = "UPDATE Stock SET TotalPillow-=1, TotalMatress-=1," +
                            " TotalMatressBase-=1 WHERE BuildingNumber='"+student.getBuildingNo()+"';";//Decrementing the stock on every student allocation.

                    updateStatus &= javaConnection.updateQuery(query);
                    it.remove();//Removing the student from memory after allocation.
                    remainingStudents--;
                    System.out.println("Remaining students: "+ remainingStudents);
                }
            }
        }

        return updateStatus;
    }

    public void loadAvailableDorms(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM AvailableDorm ORDER BY NumberOfStudents ASC OFFSET "+(getPageNumber()-1)*ROW_PER_PAGE+
                " ROWS FETCH NEXT "+ROW_PER_PAGE+" ROWS ONLY";
        ResultSet resultSet = javaConnection.selectQuery(query);
        System.out.println("Query: "+query);

        try{
            availableDorms.clear();//Erasing previously loaded dorms.
            while(resultSet.next()){
                String roomNo = resultSet.getString("roomNumber");
                String buildingNumber = resultSet.getString("buildingNumber");
                int maxCapacity = resultSet.getInt("maxCapacity");
                Dormitory tmpDorm = new Dormitory(roomNo,buildingNumber,maxCapacity);
                tmpDorm.setNoOfStudents(resultSet.getInt("NumberOfStudents"));
                tmpDorm.setNoOfLockers(resultSet.getInt("Lockers"));
                tmpDorm.setDormType(resultSet.getString("DormType"));
                availableDorms.add(tmpDorm);
            }
        } catch (SQLException ex){
            ex.printStackTrace();//For debugging only.
        }
    }

    public int getTotalStudentNo(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet;
        int totalNewStudents = 0;
        resultSet = javaConnection.selectQuery(query);
        try{
            while(resultSet.next()){
                totalNewStudents = resultSet.getInt("TotalNo");
            }
        } catch (SQLException ex){
            ex.printStackTrace();//For debugging only.
        }
        return totalNewStudents;
    }

    public int getTotalSpace(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT SUM(MaxCapacity) - SUM(NumberOfStudents) AS TotalSpace FROM AvailableDorm WHERE (MaxCapacity - NumberOfStudents)>lockers";
        ResultSet resultSet;
        int totalSpace = 0;
        resultSet = javaConnection.selectQuery(query);
        try{
            while(resultSet.next()){
                totalSpace = resultSet.getInt("TotalSpace");
            }
        } catch (SQLException ex){
            ex.printStackTrace();//For debugging only.
        }
        return totalSpace;
    }

    public void updateDormInfo(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query;
        for(Dormitory dorm: availableDorms){
            query = "UPDATE Dorm SET KeyHolder='"+dorm.getKeyHolderId()+
                    "' WHERE BuildingNumber='"+dorm.getBuildingNo()+"' AND RoomNumber='"+dorm.getRoomNO()+"' ";
            javaConnection.updateQuery(query);
        }
    }
    private void loadNewStudents(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM STUDENT WHERE BuildingNumber IS NULL AND RoomNumber IS NULL " +
                "AND Place != 'ADDIS ABABA' AND isEligible = 1 ORDER BY Fname OFFSET "+(getPageNumber()-1)*ROW_PER_PAGE+
                " ROWS FETCH NEXT "+ROW_PER_PAGE+" ROWS ONLY";
        ResultSet resultSet = javaConnection.selectQuery(query);

        try{
            while(resultSet.next()){
                String sid = resultSet.getString("SID");
                String fName = resultSet.getString("Fname");
                String lName = resultSet.getString("Lname");
                String gender = resultSet.getString("Gender");

                Student tmpStudent = new Student(fName,lName,sid,gender);
                students.put(sid,tmpStudent);
            }
        }catch (SQLException ex){
            ex.printStackTrace();//For debugging only.
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


    public void displayUpdateStatus(boolean updateStatus){
        if(updateStatus)
            JOptionPane.showMessageDialog(parentComponent,"Allocation Successful.");
        else{
            if(remainingStudents != 0)
                JOptionPane.showMessageDialog(parentComponent,"Couldn't allocate "+ remainingStudents+" students due to some problem.\n " +
                        "Make sure there is available space.");
        }
    }
    public void insertHistory(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        if(javaConnection.isConnected()){
            javaConnection.insertQuery(query);
        }
    }
}
