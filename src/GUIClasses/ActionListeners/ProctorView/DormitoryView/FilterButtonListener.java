package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import BasicClasses.Others.JavaConnection;
import GUIClasses.ProctorViews.DormitoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class FilterButtonListener implements ActionListener {
    private DormitoryView parentComponent;
    public FilterButtonListener(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void loadDorms(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String condition = parentComponent.getSelectedCondition();
        String query = "";
        ResultSet resultSet;

        if(condition.equals("Year of Students")){
            query = "SELECT DISTINCT A.BuildingNumber, A.RoomNumber, NumberOfStudents " +
                    "FROM AvailableDorm AS A JOIN Student AS S ON A.BuildingNumber = S.BuildingNumber " +
                    "AND A.RoomNumber = S.RoomNumber AND year = "+parentComponent.getYear();
        } else {

        }
    }
}
