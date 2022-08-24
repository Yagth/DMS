package GUIClasses.ActionListeners.ProctorPage;

import GUIClasses.LoginPage;
import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.StudentViews.StudentPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutMenuItemListener extends MenuItemListener {

    public LogoutMenuItemListener(ProctorPage parentComponent){
        super(parentComponent);
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
