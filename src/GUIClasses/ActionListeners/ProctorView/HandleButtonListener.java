package GUIClasses.ActionListeners.ProctorView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.ClothTakeOutRequest;
import BasicClasses.Requests.Request;
import GUIClasses.ReportDetailView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

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
                    "VALUES('"+request.getHandledDate()+
                    "' ,'"+parentComponent.getHandlerId()+
                    "', "+request.getRequestId()+")";
            System.out.println("Query: "+query);//For debugging only.
            return javaConnection.insertQuery(query);
        } else{
            Vector<Vector<Object>> tmpClothRequest = parentComponent.getClothRequests();
            int updateStatus = 0;
            for(Vector<Object> clothTakeOutRequest: tmpClothRequest){
                if((int) clothTakeOutRequest.get(1) == parentComponent.getClothRequestId()){
                    query = "INSERT INTO ProctorApprovesClothTakeOut(handledDate,EID,clothReportId,clothCountId) " +
                            "VALUES('"+request.getHandledDate()+
                            "' ,'"+parentComponent.getHandlerId()+
                            "', "+clothTakeOutRequest.get(0)+
                            ", "+parentComponent.getClothRequestId()+")";
                    updateStatus = javaConnection.insertQuery(query);
                }
            }
            return updateStatus;
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
        parentComponent.refreshParentTable();
        parentComponent.showParentComponent();

    }
}
