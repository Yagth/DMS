package GUIClasses.ActionListeners.ProctorPage.Deallocate;

import BasicClasses.Others.JavaConnection;
import GUIClasses.ProctorViews.DeallocateDormView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class DeallocateButtonListener implements ActionListener {
    private DeallocateDormView parentComponent;
    String buildingNumber;

    public DeallocateButtonListener(DeallocateDormView parentComponent){
        this.parentComponent = parentComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(parentComponent.getSelectedCondition().equals("deallocate Batch of students")){
            buildingNumber = parentComponent.getFromBuildingNumber();
            if(!(buildingNumber.equals(""))){
                String year = parentComponent.getYearTF().getText();
                String query = null, query2 = null;
                if(year.equals("")){
                    JOptionPane.showMessageDialog(parentComponent,"Year can't be empty on this condition");
                }
                else{
                    int yearInt = Integer.parseInt(year);
                    boolean updateStatus = false;
                    query = "DELETE FROM STUDENT WHERE year="+yearInt+"WHERE BuildingNumber='\"+buildingNumber+\"';";
                    query2 = "UPDATE Stock SET TotalPillow+=(SELECT COUNT(SID) " +
                            "FROM STUDENT WHERE year="+yearInt+" WHERE BuildingNumber='"+buildingNumber+"') WHERE BuildingNumber='"+buildingNumber+"';" +
                            "SET TotalMatress+=(SELECT COUNT(SID) " +
                            "FROM STUDENT WHERE year="+yearInt+"WHERE BuildingNumber='"+buildingNumber+"' ) WHERE BuildingNumber='"+buildingNumber+"';" +
                            "SET TotalMatressBase+=(SELECT COUNT(SID) " +
                            "FROM STUDENT WHERE year="+yearInt+" WHERE BuildingNumber='"+buildingNumber+"') WHERE BuildingNumber='"+buildingNumber+"'";
                    int choice = JOptionPane.showConfirmDialog(parentComponent,"Are you sure you want to deallocate these students?",
                            "confirm",JOptionPane.YES_NO_OPTION);
                    if(choice == 0) updateStatus = deallocate(query);
                    deallocate(query2);
                    updateStatus&=updateStatus;
                    displayUpdateStatus(updateStatus);
                }
            }
            else{
                JOptionPane.showMessageDialog(parentComponent,"From building number can't be empty");
            }
        }
        else{
            buildingNumber = String.valueOf(parentComponent.getProctor().getBuildingNo());
            String query = null, query2 = null;
            boolean updateStatus = false;
            query = "DELETE FROM STUDENT WHERE isEligible="+0;
            query2 = "UPDATE Stock SET TotalPillow+=(SELECT COUNT(SID) FROM STUDENT WHERE isEligible=0) WHERE BuildingNumber='"+buildingNumber+"';" +
                    "UPDATE Stock SET TotalMatress+=(SELECT COUNT(SID) FROM STUDENT WHERE isEligible=0)WHERE BuildingNumber='"+buildingNumber+"';" +
                    "UPDATE Stock SET TotalMatress+=(SELECT COUNT(SID) FROM STUDENT WHERE isEligible=0)WHERE BuildingNumber='"+buildingNumber+"';" ;
            int choice = JOptionPane.showConfirmDialog(parentComponent,"Are you sure you want to deallocate these students?",
                    "confirm",JOptionPane.YES_NO_OPTION);
            if(choice == 0) updateStatus = deallocate(query);
            deallocate(query2);
            updateStatus&=updateStatus;
            displayUpdateStatus(updateStatus);
        }
    }
    private boolean deallocate(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        boolean updateStatus = false;
        if(javaConnection.isConnected()){
            updateStatus = javaConnection.updateQuery(query);
        }
        return updateStatus;
    }

    private void displayUpdateStatus(boolean updateStatus){
        if(updateStatus){
            JOptionPane.showMessageDialog(parentComponent,"Deallocation successful");
        }
        else{
            JOptionPane.showMessageDialog(parentComponent,"Couldn't deallocate students due to some reason.");
        }
    }
}
