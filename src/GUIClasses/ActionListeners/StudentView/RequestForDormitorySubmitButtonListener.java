package GUIClasses.ActionListeners.StudentView;

import GUIClasses.StudentViews.RequestForDormitory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestForDormitorySubmitButtonListener implements ActionListener {
    RequestForDormitory parentComponent;

    public RequestForDormitorySubmitButtonListener(RequestForDormitory parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Integer updateStatus = parentComponent.updateDataBase();
        boolean hasEmptyField = parentComponent.getWoreda().equals("") || parentComponent.getSubcity().equals("") || parentComponent.getDescription().equals("");
        if(hasEmptyField) JOptionPane.showMessageDialog(parentComponent, "Please make sure that all fields are filled", "Empty field error", JOptionPane.ERROR_MESSAGE);
        else{
            parentComponent.displayUpdateStatus(updateStatus);
            parentComponent.dispose();
            parentComponent.showParentComponent();
        }
    }
}
