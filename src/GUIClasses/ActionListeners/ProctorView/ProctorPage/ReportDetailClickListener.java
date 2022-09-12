package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.*;
import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.ReportDetailView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportDetailClickListener implements MouseListener {
    private ProctorPage parentComponent;

    public ReportDetailClickListener(JFrame parentComponent){
        this.parentComponent = (ProctorPage) parentComponent;
    }

    public Request getRequest(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet;

        JTable table = parentComponent.getReportTable();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int clickedRow = table.getSelectedRow();
        int reportId = (int) tableModel.getValueAt(clickedRow,0);
        String reportType = (String) tableModel.getValueAt(clickedRow,1);
        Request request;

        String query = "SELECT * FROM AllReports WHERE ReportId="+reportId+" AND ReportType='"+reportType+"'";

        System.out.println("ReportType from GetReport: "+reportType);//For debugging purpose.

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){

                    if(reportType.equals("ClothTakeOutForm")){
                        request = new ClothTakeOutRequest(resultSet.getString("ReporterId"));
                        request.setRequestedDate(resultSet.getDate("ReportedDate"));
                        request.setHandledDate(resultSet.getDate("HandledDate"));
                        request.setLocation(resultSet.getString("BuildingNumber")+" "+resultSet.getString("RoomNumber"));
                        request.setDescription(resultSet.getString("Description"));
                        return request;
                    }
                    else if(reportType.equals("ExtendDormStayRequest")){
                        request = new ExtendDormStayRequest(resultSet.getString("ReporterId"));
                        request.setRequestedDate(resultSet.getDate("ReportedDate"));
                        request.setHandledDate(resultSet.getDate("HandledDate"));
                        request.setLocation(resultSet.getString("BuildingNumber")+" "+resultSet.getString("RoomNumber"));
                        request.setDescription(resultSet.getString("Description"));
                        return request;
                    }
                    else if(reportType.equals("Maintenance")){
                        request = new MaintenanceRequest(resultSet.getString("ReporterId"));
                        request.setRequestedDate(resultSet.getDate("ReportedDate"));
                        request.setHandledDate(resultSet.getDate("HandledDate"));
                        request.setLocation(resultSet.getString("BuildingNumber")+" "+resultSet.getString("RoomNumber"));
                        request.setDescription(resultSet.getString("Description"));
                        return request;
                    }
                    else{
                        request = new RequestForNewDorm(resultSet.getString("ReporterId"));
                        request.setRequestedDate(resultSet.getDate("ReportedDate"));
                        request.setHandledDate(resultSet.getDate("HandledDate"));
                        request.setLocation(resultSet.getString("BuildingNumber")+" "+resultSet.getString("RoomNumber"));
                        request.setDescription(resultSet.getString("Description"));
                        return request;
                    }
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return null;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Request tmp = getRequest();
        System.out.println("ReportType: "+tmp.getRequestType());//For debugging only.
        new ReportDetailView(parentComponent,tmp,parentComponent.getProctor().getpId());
        parentComponent.setVisible(false);
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
