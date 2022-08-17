package GUIClasses.ActionListeners;

import BasicClasses.Enums.UserStatus;
import GUIClasses.LoginPage;
import GUIClasses.StudentViews.StudentPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginButtonActionListener implements ActionListener {
        LoginPage parentComponent;

        public LoginButtonActionListener(LoginPage parentComponent){
            this.parentComponent = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isUser = parentComponent.checkUser();

            if(isUser){
                if(parentComponent.getUserStatus().equals(UserStatus.STUDENT))
                    new StudentPage(parentComponent.createStudent());
                else if(parentComponent.getUserStatus().equals(UserStatus.PROCTOR))
                    new StudentPage(parentComponent.createStudent()); // This part here will be changed to proctor view when there is one created.
            }
            else{
                JOptionPane.showMessageDialog(parentComponent,"Wrong credentials Please try again","Login error",JOptionPane.ERROR_MESSAGE);
                parentComponent.clear();
                parentComponent.getUsernameTF().requestFocus();
            }
        }
}