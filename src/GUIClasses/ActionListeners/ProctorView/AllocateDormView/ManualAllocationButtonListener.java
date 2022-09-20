package GUIClasses.ActionListeners.ProctorView.AllocateDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.Request;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.AllocationForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManualAllocationButtonListener extends RequestedStudentDormAllocation {
    private AllocationForm parentComponent;


    public ManualAllocationButtonListener(AllocationForm parentComponent){
        super(parentComponent.getParentComponent());
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(parentComponent.getBuildingNumber().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(parentComponent,"Please enter valid building Number");
            return;
        }
        String query = "SELECT COUNT(*) AS TotalNo FROM AvailableDorm WHERE BuildingNumber='"+parentComponent.getBuildingNumber()+"' ";
        loadAndSetTotalPage(query);
        resetPageNumber();

        students.clear();
        allocateStudents();

        query= "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Allocate Dorm', '"+
                Request.getCurrentDate()+"' , '"+parentComponent.getProctor().getBuildingNo()+"')";

        insertHistory(query);
        parentComponent.goToParent();
    }

    @Override
    public void loadAvailableDorms(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM AvailableDorm WHERE BuildingNumber='"+parentComponent.getBuildingNumber()+
                "' ORDER BY NumberOfStudents ASC OFFSET "+(getPageNumber()-1)*ROW_PER_PAGE+
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
    @Override
    public int getTotalSpace(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT SUM(MaxCapacity) - SUM(NumberOfStudents) AS TotalSpace FROM AvailableDorm " +
                "WHERE (MaxCapacity - NumberOfStudents)>lockers AND BuildingNumber='"+parentComponent.getBuildingNumber()+"' ";
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
}
