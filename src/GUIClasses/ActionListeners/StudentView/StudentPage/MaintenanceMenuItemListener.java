package GUIClasses.ActionListeners.StudentView.StudentPage;

import GUIClasses.StudentViews.MaintenanceRequestForm;
import GUIClasses.StudentViews.StudentPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceMenuItemListener extends MenuItemListener implements ActionListener {
    public MaintenanceMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new MaintenanceRequestForm(parentComponent.getStudent(),parentComponent);
        hideParentComponent();
    }
}
