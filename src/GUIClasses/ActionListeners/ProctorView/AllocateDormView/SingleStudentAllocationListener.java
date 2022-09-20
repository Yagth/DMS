package GUIClasses.ActionListeners.ProctorView.AllocateDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.Request;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.SingleStudentAllocationForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleStudentAllocationListener implements ActionListener {
    private SingleStudentAllocationForm parentComponent;
    private String buildingNumber;
    private String roomNumber;
    private Student student;
    public SingleStudentAllocationListener(SingleStudentAllocationForm parentComponent){
        this.parentComponent = parentComponent;
        buildingNumber = parentComponent.getBuildingNumber();
        roomNumber = parentComponent.getRoomNumber();
        student = parentComponent.getStudent();
    }
    public boolean allocateStudent(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM AvailableDorm WHERE BuildingNumber='"
                +buildingNumber+"' AND RoomNumber='"+roomNumber+"' ";

        ResultSet resultSet = javaConnection.selectQuery(query);
        Dormitory tmpDorm = null;
        boolean dormIsValid;
        boolean updateStatus = false;
        try{
            while(resultSet.next()){
                tmpDorm = new Dormitory(roomNumber,buildingNumber,resultSet.getInt("maxCapacity"));
                tmpDorm.setNoOfLockers(resultSet.getInt("Lockers"));
                tmpDorm.setNoOfStudents(resultSet.getInt("NumberOfStudents"));
                tmpDorm.setDormType(resultSet.getString("DormType"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();//For debugging only.
        }

        dormIsValid = dormIsValid(tmpDorm);

        if(!dormIsValid) {
            JOptionPane.showMessageDialog(parentComponent, "Can't allocate student to this dorm"
                    , "Allocation error.", JOptionPane.ERROR_MESSAGE);
        }else{
            JavaConnection javaConnection1 = new JavaConnection(JavaConnection.URL);
            query = "UPDATE Student SET BuildingNumber='"+buildingNumber+"', SET RoomNumber='"
                    +roomNumber+"' WHERE SID='"+student.getsId()+"' ";
            updateStatus = javaConnection1.updateQuery(query);
            query = "UPDATE Stock SET TotalPillow-=1, TotalMatress-=1," +
                    " TotalMatressBase-=1 WHERE BuildingNumber='"+student.getBuildingNo()+"';";
            updateStatus &= javaConnection1.updateQuery(query);
            query = "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                    " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Allocate Dorm', '"+
                    Request.getCurrentDate()+"' , '"+parentComponent.getProctor().getBuildingNo()+"')";
            insertHistory(query);
        }
       return updateStatus;
    }

    public void insertHistory(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        if(javaConnection.isConnected()){
            javaConnection.insertQuery(query);
        }
    }
    public boolean dormIsValid(Dormitory dorm){
        boolean hasSpace;
        boolean hasLocker;
        boolean isRightGender;

        hasSpace = dorm.getNoOfStudents()<dorm.getMaxCapacity();
        hasLocker = dorm.getNoOfStudents()<dorm.getNoOfLockers();
        isRightGender = dorm.getDormType().equalsIgnoreCase(student.getGender());

        return hasSpace & hasLocker & isRightGender;
    }

    public void displayUpdateStatus(boolean updateStatus){
        if(updateStatus)
            JOptionPane.showMessageDialog(parentComponent,"Allocation Successful.");
        else{
            JOptionPane.showMessageDialog(parentComponent,"Couldn't allocate students due to some problem.\n " +
                    "Make sure there is available space.");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean updateStatus = allocateStudent();
    }
}
