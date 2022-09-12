package GUIClasses.ActionListeners.ProctorView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.Request;
import GUIClasses.ReportDetailView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class HandleButtonListener implements ActionListener {
    ReportDetailView parentComponent;
    public HandleButtonListener(ReportDetailView parentComponent){
        this.parentComponent = parentComponent;
    }

    public int updateDataBase(Request request){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "";
        if(!(request.getRequestType().equals("ClothTakeOutForm"))){
            query = "INSERT INTO ProctorHandlesReport(handledDate,EID,ReportId) " +
                    "VALUES(handledDate="+request.getHandledDate()+
                    " ,EID='"+parentComponent.getHandlerId()+
                    "', ReportId="+request.getRequestId()+")";
            System.out.println("Query: "+query);//For debugging only.
            return javaConnection.insertQuery(query);
        } else{
            query = "INSERT INTO ProctorApprovesClothTakeOut(handledDate,EID,clothReportId,clothCountId) " +
                    "VALUES('"+request.getHandledDate()+
                    "' ,'"+parentComponent.getHandlerId()+
                    "', "+request.getRequestId()+
                    ", "+parentComponent.getClothRequestId()+")";
            System.out.println("Query: "+query);//For debugging only.
            return javaConnection.insertQuery(query);
        }
    }

    public void displayUpdateStatus(boolean updateStatus){
        if(!updateStatus) JOptionPane.showMessageDialog(parentComponent,"Couldn't handle report due to connection error");
        else JOptionPane.showMessageDialog(parentComponent,"Handle successfully completed");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Date currentDate = Request.getCurrentDate();
        Request tmpRequest = parentComponent.getRequest();
        int choice = JOptionPane.showConfirmDialog(parentComponent,"Are you sure you handled this request?",
                "Confirm Message", JOptionPane.YES_NO_OPTION);
        if(choice == 1) return;
        tmpRequest.setHandledDate(currentDate);
        int tmp = updateDataBase(tmpRequest);
        boolean updateStatus = (tmp == 1);

        displayUpdateStatus(updateStatus);
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
