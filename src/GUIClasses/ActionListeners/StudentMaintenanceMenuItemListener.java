package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.MaintenanceRequestForm;
import GUIClasses.StudentViews.StudentPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentMaintenanceMenuItemListener extends StudentPageMenutItemListener implements ActionListener {
    public StudentMaintenanceMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new MaintenanceRequestForm(parentComponent.getUser(),parentComponent);
        hideParentComponent();
    }
}
