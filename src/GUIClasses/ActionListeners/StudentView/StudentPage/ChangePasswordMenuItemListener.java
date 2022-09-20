package GUIClasses.ActionListeners.StudentView.StudentPage;

import GUIClasses.ChangePasswordForm;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordMenuItemListener extends MenuItemListener implements ActionListener {
    public ChangePasswordMenuItemListener(StudentPage parentComponent) {
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ChangePasswordForm(parentComponent);
        parentComponent.setVisible(false);
    }
}
