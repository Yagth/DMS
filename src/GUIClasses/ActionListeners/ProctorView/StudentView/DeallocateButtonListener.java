package GUIClasses.ActionListeners.ProctorView.StudentView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.Request;
import GUIClasses.ProctorViews.StudentDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeallocateButtonListener implements ActionListener {
    private StudentDetailView parentComponent;
    public DeallocateButtonListener(StudentDetailView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(parentComponent,"Are you sure you want to deallocate this dorm"
                ,"Confirm Deallocation",JOptionPane.YES_NO_OPTION);
        if(choice == 1) return;
        else{
            boolean updateStatus = deallocateStudent();
            String query = "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                    " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Allocate Dorm', '"+
                    Request.getCurrentDate()+"' , '"+parentComponent.getProctor().getBuildingNo()+"')";
            insertHistory(query);
            displayUpdateStatus(updateStatus);
            parentComponent.reloadParentTable();
            parentComponent.goBackToParent();
        }
    }

    public boolean deallocateStudent(){
        String buildingNumber = String.valueOf(parentComponent.getStudent().getBuildingNo());
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "UPDATE Student SET buildingNumber=null, roomNumber=null,pillow=0,matress=0,bedBase=0 WHERE SID='"+parentComponent.getStudentID()+"'";
        boolean updateStatus = false;

        if(javaConnection.isConnected()){
            updateStatus = javaConnection.updateQuery(query);
            query = "UPDATE Stock SET TotalPillow+=1, TotalMatress+=1," +
                    " TotalMatressBase+=1 WHERE BuildingNumber='"+buildingNumber+"';";
            updateStatus &= javaConnection.updateQuery(query);
        }

        return updateStatus;
    }

    public void insertHistory(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        if(javaConnection.isConnected()){
            javaConnection.insertQuery(query);
        }
    }
    public void displayUpdateStatus(boolean updateStatus){
        if(updateStatus) JOptionPane.showMessageDialog(parentComponent,"Deallocation successful");
        else JOptionPane.showMessageDialog(parentComponent,"Couldn't perform operation");
    }
}
