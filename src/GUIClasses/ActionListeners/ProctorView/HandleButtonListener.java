package GUIClasses.ActionListeners.ProctorView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.Request;
import GUIClasses.ReportDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class HandleButtonListener implements ActionListener {
    ReportDetailView parentComponent;
    public HandleButtonListener(ReportDetailView parentComponent){
        this.parentComponent = parentComponent;
    }

    public int updateDataBase(Request request){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "";
        if(!(request.getRequestType().equals("ClothTakeOutForm") || request.getRequestType().equals("RequestForNewDorm"))){
            query = "INSERT INTO ProctorHandlesReport(handledDate,EID,ReportId) " +
                    "VALUES('"+request.getHandledDate()+
                    "' ,'"+parentComponent.getHandlerId()+
                    "', "+request.getRequestId()+")";
            System.out.println("Query in first if: "+query);//For debugging only.
            return javaConnection.insertQuery(query);
        } else if(request.getRequestType().equals("RequestForNewDorm")) {
            return automaticAllocation(request);
        } else{
            Vector<Vector<Object>> tmpClothRequest = parentComponent.getClothRequests();
            int updateStatus = 0;
            for(Vector<Object> clothTakeOutRequest: tmpClothRequest){
                if((int) clothTakeOutRequest.get(1) == parentComponent.getClothRequestId()){
                    query = "INSERT INTO ProctorApprovesClothTakeOut(handledDate,EID,clothReportId) " +
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

    public int automaticAllocation(Request request){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT TOP 1 * FROM AvailableDorm ORDER BY NumberOfStudents ASC";
        System.out.println("Query in second if: "+query);//For debugging only.
        ResultSet resultSet = javaConnection.selectQuery(query);
        try{
            String buildingNumber = null;
            String roomNumber = null;
            while(resultSet.next()){
                buildingNumber = resultSet.getString("BuildingNumber");
                roomNumber = resultSet.getString("RoomNumber");
            }
            query = "UPDATE Student SET BuildingNumber='"+buildingNumber+"', RoomNumber='"+roomNumber+
                    "' WHERE SID='"+request.getRequesterId()+"'";
            System.out.println("Query: "+query);//For debugging only.
            boolean updateStatus = javaConnection.updateQuery(query);
            query = "INSERT INTO ProctorHandlesReport(handledDate,EID,ReportId) " +
                    "VALUES('"+request.getHandledDate()+
                    "' ,'"+parentComponent.getHandlerId()+
                    "', "+request.getRequestId()+")";
            if(javaConnection.insertQuery(query) == 1 && updateStatus) return 1;
            else return 0;
        } catch (SQLException ex){
            return 0;
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
