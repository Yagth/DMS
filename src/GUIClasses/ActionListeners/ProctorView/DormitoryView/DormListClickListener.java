package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.DormitoryDetailView;
import GUIClasses.ProctorViews.DormitoryView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DormListClickListener implements MouseListener {
    private DormitoryView parentComponent;
    public DormListClickListener(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Dormitory dorm = getClickedDorm();
        dorm = updateDormData(dorm);
        parentComponent.setVisible(false);
        new DormitoryDetailView(dorm,parentComponent.getProctor(),parentComponent);
    }

    public Dormitory getClickedDorm(){
        JTable table = parentComponent.getTable();
        int clickedRow = table.getSelectedRow();
        if(clickedRow == -1) clickedRow = 0;
        System.out.println("Clicked row: "+clickedRow);//For debugging only.
        Dormitory tmp = parentComponent.getDormAt(clickedRow);
        return tmp;
    }

    public Dormitory updateDormData(Dormitory dormitory){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM Dorm WHERE BuildingNumber='"
                        +dormitory.getBuildingNo()+"' AND RoomNumber='"+dormitory.getRoomNO()+"'";
        ResultSet resultSet;
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    dormitory.setKeyHolderId(resultSet.getString("KeyHolder"));
                    dormitory.setNoOfLockers(resultSet.getInt("Lockers"));
                    dormitory.setNoOfTables(resultSet.getInt("StudyTable"));
                    dormitory.setNoOfChairs(resultSet.getInt("chair"));
                    dormitory.setNoOfBeds(resultSet.getInt("BedNumber"));
                    dormitory.setDormType(resultSet.getString("dormType"));
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return dormitory;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
