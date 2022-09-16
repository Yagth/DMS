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
    private JLabel logoL;
    private JPanel logoPanel;
    private JPanel leftPanel;
    private JLabel titleL;
    private JLabel subtitleL;
    private JLabel descripotionL;
    private JLabel loginL;
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

        ImageIcon usernameIcon = new ImageIcon("Images/user.png");
        ImageIcon keyIcon = new ImageIcon("Images/key.png");
        ImageIcon logo = new ImageIcon("Images/AAULOGO.png");

        logoL.setIcon(logo);
        userNameL.setIcon(usernameIcon);
        passwordL.setIcon(keyIcon);

        titleL.setFont(new Font("Californian FB",Font.BOLD,26));
        titleL.setText("<html>Welcome to Addis Ababa<br>University</html>");
        subtitleL.setFont(new Font("Californian FB",Font.BOLD,22));
        subtitleL.setText("<html>Dormitory Management<br>System</html>");
        descripotionL.setFont(new Font("Californian FB",Font.PLAIN,16));
        loginL.setFont(new Font("Californian FB",Font.BOLD,22));

        userNameTF.setBorder(BorderFactory.createEmptyBorder());
        passwordTF.setBorder(BorderFactory.createEmptyBorder());
        userNameL.setFont(new Font("Californian FB",Font.PLAIN,16));
        passwordL.setFont(new Font("Californian FB",Font.PLAIN,16));

        this.setVisible(true);
    }

    public JTextField getUserNameTF() {
        return userNameTF;
    }

    public JPasswordField getPasswordTF() {
        return passwordTF;
    }
}
