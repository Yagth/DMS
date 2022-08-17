package GUIClasses;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import BasicClasses.Persons.Student;
import GUIClasses.Interfaces.Views;
import GUIClasses.StudentViews.StudentPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame implements Views {
    private JPanel MainPanel;
    private JPanel BottomPanel;
    private JPanel CentralPanel;
    private JPanel TopPanel;
    private JLabel Tittle;
    private JTextField Textfield;
    private JPasswordField passwordField;
    private JButton LoginButton;
    private JLabel Username;
    private JLabel Password;
    private JPanel centralCenter;
    private JPanel WestSpace;
    private JLabel DMS;
    JavaConnection javaConnection;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 400;


    public LoginPage(){
        setUpGUi();
        javaConnection = new JavaConnection("jdbc:sqlserver://DESKTOP-AA4PR2S\\SQLEXPRESS;" +
                "DatabaseName=DMS;encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;");
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Dormitory Management System");
        this.setContentPane(MainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        LoginButton.addActionListener(new LoginActionListener(this));
    }

    public String getUsername(){
        return Username.getText();
    }

    public String getPassword(){
        return Password.getText();
    }

    public void clear(){
        Username.setText("");
        Password.setText("");
    }

    public boolean checkUser(){
        String query = "SELECT * FROM Student WHERE SID="+getUsername()+"AND Password="+getPassword();
        ResultSet temp;

        temp = javaConnection.selectQuery(query);

        if(temp.equals(null)){     //If the result set is null, the user might be Proctor.
            query = "SELECT * FROM Proctor WHERE EID="+getUsername()+"AND Password="+getPassword();
            temp = javaConnection.selectQuery(query);
        }
        return !(temp.equals(null)); // If the temp is still null, the user doesn't exist.
    }

    public Student createStudent(){
        Student student = null;
        String query = "SELECT * FROM Student WHERE EID="+getUsername()+"AND Password="+getPassword();
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
        String query = "SELECT * FROM Proctor WHERE EID="+getUsername()+"AND Password="+getPassword();
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


   public class LoginActionListener implements ActionListener {
        LoginPage parentComponent;

        public LoginActionListener(LoginPage parentComponent){
            this.parentComponent = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isUser = parentComponent.checkUser();
            parentComponent.Username.requestFocus();

            if(isUser){

            }
        }
    }
}

