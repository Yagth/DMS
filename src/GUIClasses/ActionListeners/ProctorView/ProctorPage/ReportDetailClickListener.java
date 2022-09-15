package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.*;
import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.ReportsView;
import GUIClasses.ReportDetailView;
import GUIClasses.StudentViews.SeeYourRequests;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ReportDetailClickListener implements MouseListener {
    private ProctorPage parentComponent;
    private ReportsView reportsViewParentComponent;

    public ReportDetailClickListener(JFrame parentComponent){

        try{
            this.parentComponent = (ProctorPage) parentComponent;
        }catch (ClassCastException ex){
            this.reportsViewParentComponent = (ReportsView) parentComponent;
        }
    }

    public Request getRequest(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet;
        JTable table;

        if(parentComponent == null) table = reportsViewParentComponent.getReportTable();
        else table = parentComponent.getReportTable();

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int clickedRow = table.getSelectedRow();
        int reportId = (int) tableModel.getValueAt(clickedRow,0);
        String reportType = (String) tableModel.getValueAt(clickedRow,1);
        Request request;

        String query = "SELECT * FROM AllReports WHERE ReportId="+reportId+" AND ReportType='"+reportType+"'";
        
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){

                    if(reportType.equals("ClothTakeOutForm")){
                        int tempId = resultSet.getInt("ReportId");
                        request = new ClothTakeOutRequest(resultSet.getString("ReporterId"));
                        request.setRequestedDate(resultSet.getDate("ReportedDate"));
                        request.setHandledDate(resultSet.getDate("HandledDate"));
                        request.setLocation(resultSet.getString("BuildingNumber")+"-"+resultSet.getString("RoomNumber"));
                        request.setDescription(resultSet.getString("Description"));
                        request.setRequestId(tempId);
                        System.out.println("Temp ID: "+tempId);//For debugging only.
                        return request;
                    }
                    else if(reportType.equals("ExtendDormStayRequest")){
                        request = new ExtendDormStayRequest(resultSet.getString("ReporterId"));
                        request.setRequestedDate(resultSet.getDate("ReportedDate"));
                        request.setHandledDate(resultSet.getDate("HandledDate"));
                        request.setLocation(resultSet.getString("BuildingNumber")+" "+resultSet.getString("RoomNumber"));
                        request.setDescription(resultSet.getString("Description"));
                        request.setRequestId(resultSet.getInt("ReportId"));
                        return request;
                    }
                    else if(reportType.equals("Maintenance")){
                        request = new MaintenanceRequest(resultSet.getString("ReporterId"));
                        request.setRequestedDate(resultSet.getDate("ReportedDate"));
                        request.setHandledDate(resultSet.getDate("HandledDate"));
                        request.setLocation(resultSet.getString("BuildingNumber")+" "+resultSet.getString("RoomNumber"));
                        request.setDescription(resultSet.getString("Description"));
                        request.setRequestId(resultSet.getInt("ReportId"));
                        return request;
                    }
                    else{
                        request = new RequestForNewDorm(resultSet.getString("ReporterId"));
                        request.setRequestedDate(resultSet.getDate("ReportedDate"));
                        request.setHandledDate(resultSet.getDate("HandledDate"));
                        request.setLocation(resultSet.getString("BuildingNumber")+" "+resultSet.getString("RoomNumber"));
                        request.setDescription(resultSet.getString("Description"));
                        request.setRequestId(resultSet.getInt("ReportId"));
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
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT Fname,Lname FROM STUDENT WHERE SID='"+tmp.getRequesterId()+"'";
        ResultSet resultSet;
        String reporterName="";

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    reporterName = resultSet.getString("Fname")+" "
                            +resultSet.getString("Lname");
                }
            }catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        if(parentComponent == null) {
            ReportDetailView reportDetailView = new ReportDetailView(reportsViewParentComponent,tmp,reporterName);
            reportDetailView.setHandlerId(reportsViewParentComponent.getProctorId());
            reportsViewParentComponent.setVisible(false);
        } else{
            ReportDetailView reportDetailView = new ReportDetailView(parentComponent,tmp,reporterName);
            reportDetailView.setHandlerId(parentComponent.getProctor().getpId());
            parentComponent.setVisible(false);
        }
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
