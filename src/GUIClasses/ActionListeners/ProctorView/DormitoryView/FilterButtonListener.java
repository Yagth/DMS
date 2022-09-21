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
        dorms.clear();
        String query = checkCondition();
        if(query.equals(""))
            return;
        loadDorms(query);
        parentComponent.changeTableData(dorms);
        if(dorms.size() == 0)
            JOptionPane.showMessageDialog(parentComponent,"No dorms found with this condition");
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
                    tmp.setNoOfStudents(resultSet.getInt("NumberOfStudents"));
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
            int year = parentComponent.getYear();
            if(year == 0) return "";
            query = "SELECT DISTINCT A.BuildingNumber, A.RoomNumber, maxCapacity, NumberOfStudents " +
                    "FROM AvailableDorm AS A JOIN Student AS S ON A.BuildingNumber = S.BuildingNumber " +
                    "AND A.RoomNumber = S.RoomNumber AND year = "+parentComponent.getYear()+" ORDER BY NumberOfStudents "+
                    " OFFSET "+(parentComponent.getPageNumber()-1)* parentComponent.getRowPerPage()+
                    " ROWS FETCH NEXT "+parentComponent.getRowPerPage()+" ROWS ONLY;";

        } else if(condition.equals("Available space")){
            query = "SELECT DISTINCT A.BuildingNumber, A.RoomNumber, maxCapacity, NumberOfStudents " +
                    "FROM AvailableDorm AS A LEFT JOIN Student AS S ON A.BuildingNumber = S.BuildingNumber " +
                    "AND A.RoomNumber = S.RoomNumber ORDER BY NumberOfStudents ASC "+
                    " OFFSET "+(parentComponent.getPageNumber()-1)* parentComponent.getRowPerPage()+
                    " ROWS FETCH NEXT "+parentComponent.getRowPerPage()+" ROWS ONLY;";
        } else{
            query = "SELECT BuildingNumber, RoomNumber, maxCapacity, NumberOfStudents " +
                    "FROM AvailableDorm ORDER BY(SELECT NULL)"+
                    " OFFSET "+(parentComponent.getPageNumber()-1)* parentComponent.getRowPerPage()+
                    " ROWS FETCH NEXT "+parentComponent.getRowPerPage()+" ROWS ONLY;";
        }
        System.out.println("Query: "+query);//For debugging only.
        return query;
    }
}
