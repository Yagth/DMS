package GUIClasses;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Enums.UserStatus;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import BasicClasses.Persons.Student;
import GUIClasses.ActionListeners.LoginButtonActionListener;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends JFrame{
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
    private JavaConnection javaConnection;
    UserStatus userStatus;
    public static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    public static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();



    public LoginPage(){
        setUpGUi();
    }

    public void setJavaConnection(JavaConnection javaConnection){
        this.javaConnection = javaConnection;
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
        ImageIcon logo = new ImageIcon("Images/DMS-logo.png");

        Image titleLogo = logo.getImage();

        this.setIconImage(titleLogo);

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

        submitButton.addActionListener(new LoginButtonActionListener(this));

        userNameTF.requestFocus();

        this.setVisible(true);
    }

    public JTextField getUserNameTF() {
        return userNameTF;
    }

    public JPasswordField getPasswordTF() {
        return passwordTF;
    }

    public JTextField getUsernameTF(){
        return userNameTF;
    }
    public String getUsername(){
        return userNameTF.getText();
    }

    public String getPassword(){
        return passwordTF.getText();
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void clear(){
        userNameTF.setText("");
        passwordTF.setText("");
    }
    public void checkAndSetUserStatus(){
        String tmp = getUsername().substring(0,3);
        if(tmp.equalsIgnoreCase("UGR") || tmp.equalsIgnoreCase("PGR")) userStatus = UserStatus.STUDENT; // If the user is undergraduate(UGR) or is postgraduate(PGR).
        else if(tmp.equalsIgnoreCase("EMP")) userStatus = UserStatus.PROCTOR;  //If the user is employee(EMP).
    }

    public boolean checkUser(){
        String query;
        ResultSet temp = null;
        boolean isUser = false;
        checkAndSetUserStatus();
        try{
            if (userStatus.equals(UserStatus.STUDENT)){
                query = "SELECT * FROM Student WHERE SID=\'"+getUsername()+"'";
                temp = javaConnection.selectQuery(query);
            }
            else if(userStatus.equals(UserStatus.PROCTOR)){     //If the result set is null, the user might be Proctor.
                query = "SELECT * FROM Proctor WHERE EID=\'"+getUsername()+"'";
                temp = javaConnection.selectQuery(query);
            }
            if(temp.next()){
                String password = temp.getString("Password");
                if(getPassword().equals(password))
                    isUser = true; //This checks whether there is a user that matches the credentials.
            }
        }
        catch (NullPointerException ex){
            userStatus = null;
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Sorry something went wrong", "Unknown error",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // This part here is only for debugging purpose.
        }
        return isUser; // If the temp is still null, the user doesn't exist.
    }

    public Student createStudent(){
        Student student = null;
        String query = "SELECT * FROM Student WHERE SID=\'"+getUsername()+"\' AND Password=\'"+getPassword()+"\'";
        ResultSet temp = javaConnection.selectQuery(query);
        try {
            while (temp.next()) {
                student = new Student(temp.getString("Fname"),
                        temp.getString("Lname"),
                        getUsername(),temp.getString("Gender"));
                student.setDepartment(temp.getString("Department"));
                student.setYear(temp.getInt("Year"));
                student.setBuildingNo(temp.getString("BuildingNumber"));
                student.setDormNo(temp.getString("RoomNumber"));
                student.setEligibility(temp.getBoolean("isEligible"));
                student.setPlaceOfOrigin(temp.getString("place"));

                boolean noDorm = student.getBuildingNo() == 0 & student.getBuildingNo() == 0;
                if(noDorm)
                    JOptionPane.showMessageDialog(null,"You don't have dormitory yet.");
            }

        }catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Couldn't login due to connection error.",
                    "Login error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // This part is only debugging purpose.
        }
        return student;
    }

    public Proctor createProctor(){
        Proctor proctor = null;
        String query = "SELECT * FROM ProctorBuilding WHERE EID=\'"+getUsername()+"\' AND Password=\'"+getPassword()+"\'";
        ResultSet temp = javaConnection.selectQuery(query);
        try {
            while (temp.next()) {
                proctor = new Proctor(temp.getString("Fname"),temp.getString("Lname")
                        ,temp.getString("Gender"));
                proctor.setBuildingNo(temp.getString("BuildingNumber"));
                proctor.setpId(getUsername());
            }

        }catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Couldn't login due to connection error.",
                    "Login error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // This part is only debugging purpose.
        }
        return proctor;
    }
}
