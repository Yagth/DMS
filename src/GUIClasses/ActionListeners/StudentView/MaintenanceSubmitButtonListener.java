package GUIClasses.ActionListeners.StudentView;

import GUIClasses.StudentViews.MaintenanceRequestForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceSubmitButtonListener implements ActionListener {

    private MaintenanceRequestForm parentComponent;
    public MaintenanceSubmitButtonListener(MaintenanceRequestForm parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean hasEmptyField = (parentComponent.getBlockNumber().equals("")||parentComponent.getRoomNumber().equals("")||parentComponent.getDescription().equals(""));
        if(hasEmptyField) JOptionPane.showMessageDialog(parentComponent, "Please make sure that all fields contain the appropriate information.","Empty field",JOptionPane.ERROR_MESSAGE);
        else{
            Integer updateStatus = parentComponent.updateDataBase();
            parentComponent.displayUpdateStatus(updateStatus);
            parentComponent.dispose();
            parentComponent.showParentComponent();
        }

    }
}
