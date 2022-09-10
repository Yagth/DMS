package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.DormitoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class SearchButtonListener implements ActionListener {
    private DormitoryView parentComponent;
    private ArrayList<Dormitory> dorms;

    public SearchButtonListener(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
        dorms = new ArrayList<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        loadSearchedDorm();
        parentComponent.changeTableData(dorms);
    }
    public void loadSearchedDorm(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String buildingNumber = parentComponent.getBuildingNo();
        String roomNumber = parentComponent.getRoomNo();
        String query = "";
        ResultSet resultSet;

        if((!buildingNumber.equals("")) & (!roomNumber.equals(""))){
            query = "SELECT BuildingNumber, RoomNumber, maxCapacity, NumberOfStudents " +
                    "FROM DormAndStudentNo WHERE BuildingNumber='"+buildingNumber+"' AND RoomNumber='"+roomNumber+"'";
        } else if(!(buildingNumber.equals(""))){
            query = "SELECT BuildingNumber, RoomNumber, maxCapacity, NumberOfStudents " +
                    "FROM DormAndStudentNo WHERE BuildingNumber='"+buildingNumber+"'";
        } else if(!(roomNumber.equals(""))) {
            query = "SELECT BuildingNumber, RoomNumber, maxCapacity, NumberOfStudents " +
                    "FROM DormAndStudentNo WHERE RoomNumber='" + roomNumber + "'";
        }
        if(query.equals("")){
            JOptionPane.showMessageDialog(parentComponent,"Please make sure to enter the\n appropriate information before you search",
                    "Invalid input",JOptionPane.ERROR_MESSAGE);
        } else{
            if(javaConnection.isConnected()){
                resultSet = javaConnection.selectQuery(query);
                Dormitory tmp;
                try{
                    while(resultSet.next()){
                        tmp = new Dormitory(resultSet.getString("BuildingNumber"),
                                resultSet.getString("RoomNumber"),
                                resultSet.getInt("maxCapacity")
                        );
                        tmp.setNoOfStudents(resultSet.getInt("NumberOfStudent"));
                        dorms.add(tmp);
                    }
                } catch (SQLException ex){
                    ex.printStackTrace();//For debugging only.
                }
            }
        }
    }
}
