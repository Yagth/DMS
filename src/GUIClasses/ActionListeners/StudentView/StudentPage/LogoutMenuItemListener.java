package GUIClasses.ActionListeners.StudentView.StudentPage;

import GUIClasses.LoginPage;
import GUIClasses.StudentViews.StudentPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutMenuItemListener implements ActionListener {
    private StudentPage parentComponent;

    public LogoutMenuItemListener(StudentPage parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(parentComponent,"Are you sure you want to logout?","Confirm logout",JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            parentComponent.dispose();
            new LoginPage();
        }
    }
}
