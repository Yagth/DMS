package GUIClasses;

import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangePasswordForm extends JFrame implements Views {
    private JPanel mainPanel;
    private JPasswordField oldPassTF;
    private JPasswordField newPassTF;
    private JPasswordField ConfirmPassTF;
    private JButton changeButton;
    private JFrame parentComponent;

    public ChangePasswordForm(JFrame parentComponent){
        this.parentComponent = parentComponent;
        setUpGUi();
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Change password");
        this.setContentPane(mainPanel);
        this.setSize(400,200);
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

        this.setVisible(true);
    }
}
