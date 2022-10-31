package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToBuildingNoTFListener implements FocusListener {
    private ChangeDormView parentComponent;
    private ArrayList<Dormitory> availableDorms;

    public ToBuildingNoTFListener(ChangeDormView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void focusGained(FocusEvent e) {
        availableDorms = new ArrayList<>();
    }

    @Override
    public void focusLost(FocusEvent e) {
        loadAvailableDorms();
        int availableDorms = getTotalAvailableSpace();
        parentComponent.setAvailableSpaceLText(availableDorms);

    }

    public void loadAvailableDorms(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String toBuildingNo = parentComponent.getDestinationBuildingNo();
        String query = "SELECT * FROM AvailableDorm WHERE BuildingNumber='"+toBuildingNo+"'";
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
    public int getTotalAvailableSpace(){
        int totalSpace = 0;
        for(Dormitory dormitory: availableDorms){
            totalSpace += dormitory.getMaxCapacity() - dormitory.getNoOfStudents();
        }
        return totalSpace;
    }
}
