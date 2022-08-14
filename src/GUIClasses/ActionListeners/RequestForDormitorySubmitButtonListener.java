package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.RequestForDormitory;

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
        parentComponent.displayUpdateStatus(updateStatus);
        parentComponent.dispose();
    }
}
