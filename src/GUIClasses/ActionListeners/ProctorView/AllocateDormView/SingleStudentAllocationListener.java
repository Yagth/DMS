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
        student = parentComponent.getStudent();
    }
    public boolean allocateStudent(){
        Dormitory tmpDorm = null;
        boolean allocationIsValid;
        boolean updateStatus = false;
        buildingNumber = parentComponent.getBuildingNumber();
        roomNumber = parentComponent.getRoomNumber();
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM AvailableDorm WHERE BuildingNumber='"
                +buildingNumber+"' AND RoomNumber='"+roomNumber+"' ";

        ResultSet resultSet = javaConnection.selectQuery(query);
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

        allocationIsValid = isValid(tmpDorm);

        if(allocationIsValid) {

            JavaConnection javaConnection1 = new JavaConnection(JavaConnection.URL);
            query = "UPDATE Student SET BuildingNumber='"+buildingNumber+"', RoomNumber='"
                    +roomNumber+"', Pillow=1, BedBase=1,Matress=1 WHERE SID='"+student.getsId()+"' ";
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
    public boolean isValid(Dormitory dorm){
        boolean hasSpace;
        boolean hasLocker;
        boolean isRightGender;
        boolean isEligible;

        hasSpace = dorm.getNoOfStudents()<dorm.getMaxCapacity();
        hasLocker = dorm.getNoOfStudents()<dorm.getNoOfLockers();
        isRightGender = dorm.getDormType().equalsIgnoreCase(student.getGender());
        isEligible = student.getEligibility();

        if(!hasSpace) {
            JOptionPane.showMessageDialog(parentComponent,"The dorm doesn't have enough space");
            return false;
        } else if(!hasLocker){
            JOptionPane.showMessageDialog(parentComponent,"The dorm doesn't have enough locker");
            return false;
        } else if(!isRightGender){
            JOptionPane.showMessageDialog(parentComponent,"The dorm isn't the right gender");
            return false;
        } else if(!isEligible){
            JOptionPane.showMessageDialog(parentComponent,"The student isn't eligible");
            return false;
        }else{
            return hasSpace & hasLocker & isRightGender & isEligible;
        }
    }

    public void displayUpdateStatus(boolean updateStatus){
        if(updateStatus)
            JOptionPane.showMessageDialog(parentComponent,"Allocation Successful.");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean updateStatus = allocateStudent();
        parentComponent.dispose();
        parentComponent.getParentComponent().reloadParentTable();
        parentComponent.getParentComponent().dispose();
        parentComponent.getParentComponent().goBackToParent();
        displayUpdateStatus(updateStatus);
    }
}
