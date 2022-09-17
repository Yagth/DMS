package GUIClasses.ActionListeners.ProctorView.StudentView;

import BasicClasses.Others.JavaConnection;
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
            displayUpdateStatus(updateStatus);
            parentComponent.reloadParentTable();
            parentComponent.goBackToParent();
        }
    }

    public boolean deallocateStudent(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "UPDATE Student SET buildingNumber=null, roomNumber=null WHERE SID='"+parentComponent.getStudentID()+"'";
        boolean updateStatus = false;

        if(javaConnection.isConnected()) updateStatus = javaConnection.updateQuery(query);

        return updateStatus;
    }

    public void displayUpdateStatus(boolean updateStatus){
        if(updateStatus) JOptionPane.showMessageDialog(parentComponent,"Deallocation successful");
        else JOptionPane.showMessageDialog(parentComponent,"Couldn't perform operation");
    }
}
