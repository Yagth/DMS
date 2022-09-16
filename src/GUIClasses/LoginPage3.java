package GUIClasses;

import BasicClasses.Enums.SizeOfMajorClasses;

import javax.swing.*;
import java.awt.*;

public class LoginPage3 extends JFrame{
    private JPanel mainPanel;
    private JPanel rightPanel;
    private JButton submitButton;
    private JTextField userNameTF;
    private JPasswordField passwordTF;
    private JPanel loginPanel;
    private JLabel userNameL;
    private JLabel passwordL;
    public static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    public static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();



    public LoginPage3(){
        try{
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        setUpGUi();
    }
    public void setUpGUi() {
        this.setTitle("Dormitory Management System");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.getContentPane().setBackground(new Color(232,255,255));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        userNameTF.setBorder(null);
        passwordTF.setBorder(null);

        this.setVisible(true);
    }

}
