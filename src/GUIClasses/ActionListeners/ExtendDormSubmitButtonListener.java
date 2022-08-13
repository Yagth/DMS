package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.ExtendDormStayForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendDormSubmitButtonListener implements ActionListener {
    private ExtendDormStayForm parentComponent;
    ExtendDormSubmitButtonListener(ExtendDormStayForm parentComponent){
        this.parentComponent = parentComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer updateStatus = parentComponent.updateDataBase();
        parentComponent.displayUpdateStatus(updateStatus);
        parentComponent.dispose();
    }
}
