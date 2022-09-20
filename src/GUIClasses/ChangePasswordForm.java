package GUIClasses;

import BasicClasses.Enums.UserStatus;
import GUIClasses.ActionListeners.ChangePasswordButtonListener;
import GUIClasses.ActionListeners.ProctorView.ChangeDormView.ChangeButtonListener;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangePasswordForm extends JFrame implements Views {
    private JPanel mainPanel;
    private JPasswordField oldPassTF;
    private JPasswordField newPassTF;
    private JPasswordField confirmPassTF;
    private JButton changeButton;
    private JFrame parentComponent;
    private String id;
    private UserStatus userStatus;

    public ChangePasswordForm(JFrame parentComponent, String id){
        this.parentComponent = parentComponent;
        this.id = id;
        setUpGUi();
        setUserStatus();
    }

    public String getId() {
        return id;
    }

    public String getOldPassword(){
        return oldPassTF.getText();
    }

    public String getNewPassword(){
        return newPassTF.getText();
    }

    public String getConfirmPassword(){
        return confirmPassTF.getText();
    }

    public void clear(){
        oldPassTF.setText("");
        newPassTF.setText("");
        confirmPassTF.setText("");
    }

    public void setUserStatus(){
        if(id.substring(0,3).equals("UGR")) userStatus = UserStatus.STUDENT;
        else userStatus = UserStatus.PROCTOR;
    }

    public UserStatus getUserStatus(){
        return userStatus;
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Change password");
        this.setContentPane(mainPanel);
        this.setSize(500,300);
        this.setLocationRelativeTo(parentComponent);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        changeButton.addActionListener(new ChangePasswordButtonListener(this));

        this.setVisible(true);
    }
}
