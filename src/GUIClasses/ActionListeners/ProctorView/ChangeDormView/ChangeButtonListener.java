package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class ChangeButtonListener implements ActionListener {
    private ChangeDormView parentComponent;
    private ArrayList<Dormitory> availableDorms;
    public ChangeButtonListener(ChangeDormView parentComponent){
        this.parentComponent = parentComponent;
        availableDorms = new ArrayList<>();
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
            if(javaConnection.isConnected()){
                resultSet = javaConnection.selectQuery(query1);
                try{
                    while(resultSet.next()){
                        String roomNo = resultSet.getString("roomNumber");
                        String buildingNumber = resultSet.getString("buildingNumber");
                        int maxCapacity = resultSet.getInt("maxCapacity");
                        Dormitory tmp = new Dormitory(buildingNumber,roomNo,maxCapacity);

                        String query2 = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENTS " +
                                "WHERE BuildingNumber='"+buildingNumber+"' AND RoomNumber='"+roomNo+"'";
                        ResultSet rs = javaConnection.selectQuery(query2);

                        while(rs.next()){
                            tmp.setNoOfStudents(rs.getInt("numberOfStudents"));
                        }

                        availableDorms.add(tmp);
                    }
                } catch (SQLException ex){
                    //Leave the implementation of this block.
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

    public void sortDormOnAvailableRoom(){
        for(int i = 0; i<availableDorms.size(); i++){
            for(int j = 0; j<availableDorms.size(); j++){
                Dormitory tmp;
                if(availableDorms.get(j).getNoOfStudents() > availableDorms.get(j+1).getNoOfStudents()){
                    tmp = availableDorms.get(j+1);
                    availableDorms.set(j+1,availableDorms.get(j));
                    availableDorms.set(j,tmp);
                }
            }
        }
    }
}
