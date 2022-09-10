package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.DormitoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilterButtonListener implements ActionListener {
    private DormitoryView parentComponent;
    private ArrayList<Dormitory> dorms;
    public FilterButtonListener(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
        dorms = new ArrayList<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String query = checkCondition();
        loadDorms(query);
        parentComponent.changeTableData(dorms);
        if(dorms.size() == 0)
            JOptionPane.showMessageDialog(parentComponent,"No dorms found with this condition");
        dorms.clear();
    }

    public void loadDorms(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet;

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    Dormitory tmp = new Dormitory(resultSet.getString("RoomNumber"),
                            resultSet.getString("BuildingNumber"),
                            resultSet.getInt("maxCapacity")
                    );
                    dorms.add(tmp);
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
    }
    public String checkCondition(){

        String condition = parentComponent.getSelectedCondition();
        String query;
        if(condition.equals("Year of Students")){
            query = "SELECT DISTINCT A.BuildingNumber, A.RoomNumber, maxCapacity, NumberOfStudents " +
                    "FROM AvailableDorm AS A JOIN Student AS S ON A.BuildingNumber = S.BuildingNumber " +
                    "AND A.RoomNumber = S.RoomNumber AND year = "+parentComponent.getYear();

        } else {
            query = "SELECT DISTINCT A.BuildingNumber, A.RoomNumber, maxCapacity, NumberOfStudents " +
                    "FROM AvailableDorm AS A JOIN Student AS S ON A.BuildingNumber = S.BuildingNumber " +
                    "AND A.RoomNumber = S.RoomNumber ORDER BY NumberOfStudents ASC";
        }
        return query;
    }
}
