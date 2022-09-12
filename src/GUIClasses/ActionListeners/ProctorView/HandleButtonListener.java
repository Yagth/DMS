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
            ArrayList<ClothTakeOutRequest> tmpClothRequest = parentComponent.getClothRequests();
            int updateStatus = 0;
            for(ClothTakeOutRequest clothTakeOutRequest: tmpClothRequest){
                System.out.println("Request count: "+clothTakeOutRequest.getRequestCount());//For debugging only.
                System.out.println("Request Count form the parentComponent: "+parentComponent.getClothRequestId());//For debugging only.
                if(clothTakeOutRequest.getRequestCount() == parentComponent.getClothRequestId()){
                    query = "INSERT INTO ProctorApprovesClothTakeOut(handledDate,EID,clothReportId,clothCountId) " +
                            "VALUES('"+request.getHandledDate()+
                            "' ,'"+parentComponent.getHandlerId()+
                            "', "+request.getRequestId()+
                            ", "+parentComponent.getClothRequestId()+")";
                    updateStatus = javaConnection.insertQuery(query);
                }
            }

            System.out.println("Query: "+query);//For debugging only.
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
