package GUIClasses.ActionListeners.StudentPage;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.*;
import GUIClasses.StudentViews.SeeYourRequests;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeeYourRequestClickListener implements MouseListener {
    SeeYourRequests parentComponent;
    JavaConnection javaConnection;
    public SeeYourRequestClickListener(SeeYourRequests parentComponent){
        this.parentComponent = parentComponent;
        javaConnection =  new JavaConnection(JavaConnection.URL);
    }
    public Date loadHandledDate(int reportId){
        String query = "SELECT handledDate FROM HandledReport WHERE reportId="+reportId;
        ResultSet tmp = javaConnection.selectQuery(query);
        try{
            if(tmp.next())
                return tmp.getDate("handledDate");
            return null;
        }catch (SQLException ex){
            ex.printStackTrace(); // For debugging only.
            return null;
        }
    }

    public Request createSpecificRequest(String requestType){
        String reporterId = parentComponent.getStudent().getsId();
        switch (requestType){
            case "Maintenance":
                return new MaintenanceRequest(reporterId);
            case "ClothTakeOutForm" :
                return new ClothTakeOutRequest(reporterId);
            case "ExtendDormStayRequest":
                return new ExtendDormStayRequest(reporterId);
            case "RequestForNewDorm":
                return new RequestForNewDorm(reporterId);
        }
        return null;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        String query;
        Request request;
        JTable tmp = parentComponent.getReportListTable();
        DefaultTableModel tableModel = (DefaultTableModel) tmp.getModel();
        int row = tmp.getSelectedRow();
        int selectedId = (Integer) tableModel.getValueAt(row,0);
        String selectedType = (String) tableModel.getValueAt(row,1);
        if(selectedType.equals("ClothTakeOutForm"))
            query = "SELECT * FROM REPORT WHERE ReportId='"+selectedId+"'";
        else query = "SELECT * FROM ClothTakeOut WHERE ReportId='"+selectedId+"'";
        ResultSet resultSet = javaConnection.selectQuery(query);
        try{
            while(resultSet.next()){
                request = createSpecificRequest(resultSet.getString("ReportType"));
                request.setRequestId(selectedId);
                request.setDescription(resultSet.getString("Description"));
                request.setRequestedDate(resultSet.getDate("reportedDate"));
                String location = resultSet.getString("BuildingNumber")+resultSet.getString("RoomNumber");
                request.setLocation(location);
                Date tmpDate = loadHandledDate(selectedId);
                request.setHandledDate(tmpDate);
            }
        }catch (SQLException ex){
            ex.printStackTrace(); // For debugging only.
            JOptionPane.showMessageDialog(parentComponent,"Sorry. Couldn't show details due to unknown error try again later.");
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
