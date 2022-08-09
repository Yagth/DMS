package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.MaintenanceRequest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceSubmitButtonListener implements ActionListener {

    private MaintenanceRequest parentComponent;
    public MaintenanceSubmitButtonListener(MaintenanceRequest parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean hasEmptyField = (parentComponent.getBlockNumber() == 0||parentComponent.getRoomNumber() == 0||parentComponent.getDescription().equals(""));
        if(hasEmptyField) JOptionPane.showMessageDialog(null, "Please make sure that all fields contain the appropriate information.","Empty field",JOptionPane.ERROR_MESSAGE);
        else{
            Integer updateStatus = parentComponent.updateDataBase();
            parentComponent.displayUpdateStatus(updateStatus);
            parentComponent.dispose();
        }
    }
}