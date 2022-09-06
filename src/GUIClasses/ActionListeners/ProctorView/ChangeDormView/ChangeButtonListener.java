package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ActionListeners.ChangeBackButtonListener;
import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class ChangeButtonListener implements ActionListener {
    private ChangeDormView parentComponent;
    private HashSet<Dormitory> availableDorms;
    public ChangeButtonListener(ChangeDormView parentComponent){
        this.parentComponent = parentComponent;
        availableDorms = new HashSet<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String condition = parentComponent.getSelectedCondition();
        String buildingNo = parentComponent.getBuildingNo();
        String dormNo = parentComponent.getDormNo();
        String destinationBuildingNo = parentComponent.getDestinationBuildingNo();
        String destinationRoomNo = parentComponent.getDestinationRoomNo();
        String query;
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet = null;
        if(condition.equals("Change single student")){
            query = "UPDATE STUDENT SET BuildingNumber='"+destinationBuildingNo+"', " +
                    "RoomNumber='"+destinationRoomNo+"' " +
                    "WHERE BuildingNumber='"+buildingNo+"' AND RoomNumber='"+dormNo+"';";
        }
        else {
            String query1 = "SELECT BuildingNumber, RoomNumber, maxCapacity FROM DORM AS D WHERE (SELECT COUNT(BuildingNumber, RoomNumber) FROM STUDENT AS S WHERE D.BuildingNumber=S.BuildingNumber AND D.RoomNumber=S.RoomNumber)< maxCapacity;";
            resultSet = javaConnection.selectQuery(query1);
            try{
                while(resultSet.next()){
                    String roomNo = resultSet.getString("roomNumber");
                    String buildingNumber = resultSet.getString("buildingNumber");
                    int maxCapacity = resultSet.getInt("maxCapacity");
                    availableDorms.add(new Dormitory(buildingNumber,roomNo,maxCapacity));
                }
            } catch (SQLException ex){
                //Leave the implementation of this block.
            }
        }
    }
    public void sortAvailableDorms(){
        Dormitory tmp;
        for(Dormitory m : availableDorms){
            for(Dormitory d: availableDorms){
                if(d.get)
            }
        }
    }
}
