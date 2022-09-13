package GUIClasses.ActionListeners.StudentView;

import GUIClasses.StudentViews.ExtendDormStayForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendDormSubmitButtonListener implements ActionListener {
    private ExtendDormStayForm parentComponent;
    public ExtendDormSubmitButtonListener(ExtendDormStayForm parentComponent){
        this.parentComponent = parentComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(parentComponent.getDescription().equals(""))
            JOptionPane.showMessageDialog(null, "Can't submit an empty description", "Empty Message error", JOptionPane.ERROR_MESSAGE);
        else{
            Integer updateStatus = parentComponent.updateDataBase();
            parentComponent.displayUpdateStatus(updateStatus);
            parentComponent.dispose();
            parentComponent.showParentComponent();
        }
    }
}
