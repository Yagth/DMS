package GUIClasses.ActionListeners.ProctorView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.Request;
import GUIClasses.ReportDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class HandleButtonListener implements ActionListener {
    ReportDetailView parentComponent;
    public HandleButtonListener(ReportDetailView parentComponent){
        this.parentComponent = parentComponent;
    }

    public boolean updateDataBase(Request request){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "";
        if(!(request.getRequestType().equals("ClothTakeOutForm"))){
            query = "UPDATE AllReport SET handledDate="+request.getHandledDate()+" WHERE reportId="+request.getRequestId()+" AND ReportType != 'ClothTakeOutForm'";
            return javaConnection.updateQuery(query);
        } else{
            query = "UPDATE AllReport SET handledDate="+request.getHandledDate()+" WHERE reportId="+request.getRequestId()+" AND ReportType = 'ClothTakeOutForm'";
            return javaConnection.updateQuery(query);
        }
    }

    public void displayUpdateStatus(boolean updateStatus){
        if(!updateStatus) JOptionPane.showMessageDialog(parentComponent,"Couldn't handle report due to connection error");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Date currentDate = Request.getCurrentDate();
        Request tmpRequest = parentComponent.getRequest();
        int choice = JOptionPane.showConfirmDialog(parentComponent,"Are you sure you handled this request?");
        if(choice == 1) return;
        tmpRequest.setHandledDate(currentDate);
        boolean updateStatus = updateDataBase(tmpRequest);
        displayUpdateStatus(updateStatus);
    }
}
