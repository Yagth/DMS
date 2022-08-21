package GUIClasses;

import BasicClasses.Enums.UserStatus;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import BasicClasses.Persons.Student;
import GUIClasses.ActionListeners.LoginButtonActionListener;
import GUIClasses.Interfaces.Views;
import javax.swing.*;
import java.sql.*;

public class LoginPage extends JFrame implements Views {
    private JPanel MainPanel;
    private JPanel BottomPanel;
    private JPanel CentralPanel;
    private JPanel TopPanel;
    private JLabel Tittle;
    private JTextField UsernameTF;
    private JPasswordField passwordField;
    private JButton LoginButton;
    private JLabel Username;
    private JLabel Password;
    private JPanel centralCenter;
    private JPanel WestSpace;
    private JLabel DMS;
    JavaConnection javaConnection;
    UserStatus userStatus;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 400;


    public LoginPage(){
        setUpGUi();
        javaConnection = new JavaConnection(JavaConnection.URL);
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Dormitory Management System");
        this.setContentPane(MainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        LoginButton.addActionListener(new LoginButtonActionListener(this));
    }

    public JTextField getUsernameTF(){
        return UsernameTF;
    }
    public String getUsername(){
        return UsernameTF.getText();
    }

    public String getPassword(){
        return passwordField.getText();
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void clear(){
        Username.setText("");
        Password.setText("");
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
        String query = "SELECT * FROM Proctor WHERE EID=\'"+getUsername()+"\' AND Password=\'"+getPassword()+"\'";
        ResultSet temp = javaConnection.selectQuery(query);
        try {
            while (temp.next()) {
                proctor = new Proctor(temp.getString("Fname"),temp.getString("Lname")
                        ,temp.getString("Gender"));
                proctor.setBuildingNo(temp.getString("BuildingNumber"));
            }

        }catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Couldn't login due to connection error.",
                    "Login error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // This part is only debugging purpose.
        }
        return proctor;
    }
}

