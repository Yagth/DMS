package GUIClasses.StudentViews;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import GUIClasses.Interfaces.Views;

public class StudentPage extends JFrame implements Views {
    Student user;
    ResultSet dormMates;
    private JPanel MainPanel;
    private JPanel topPanel;
    private JLabel studentName;
    private JLabel studentID;
    private JLabel studentDormNo;
    private JLabel studentBuildingNo;
    private JLabel titleL;
    private JLabel nameL;
    private JLabel IDL;
    private JLabel dormNoL;
    private JLabel buildingNoL;
    private JPanel studentInfoPanel;
    private JLabel dormMateL;
    private JPanel dormMatesInfo;
    private JTable dormMateTable;
    private JScrollPane tableSP;
    private static final SizeOfMajorClasses WIDTH = SizeOfMajorClasses.WIDTH;
    private static final SizeOfMajorClasses HEIGHT = SizeOfMajorClasses.HEIGHT;


    public StudentPage(Student student){
        user = student;
        setUpGUi();
    }

    public StudentPage(){  //This constructor is only here for debugging purposes.
        setUpGUi();
    }

    public void loadDormMates(){
        String url = "jdbc:sqlserver://DESKTOP-AA4PR2S\\SQLEXPRESS;DatabaseName=DMS;" +
        "encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;";
        String query = "SELECT * FROM Student WHERE BuildingNumber=\'"
                +user.getBuildingNo()+"\' AND RoomNumber=\'"+user.getDormNo()+"\'";
        JavaConnection javaConnection = new JavaConnection(url);
        dormMates = javaConnection.selectQuery(query);
    }

    public void addDormMatesToView(){

    }

    @Override
    public void setUpGUi() {
        this.setTitle("My Dormitory");
        this.setContentPane(MainPanel);
        this.setSize(WIDTH.getSize(), HEIGHT.getSize());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar Services = new JMenuBar();
        Services.setBackground(new Color(72,131,184));
        JMenu Service = new JMenu("Services");
        Service.setForeground(Color.white);

        JMenuItem maintainanceReport = new JMenuItem("maintainanceReport");
        maintainanceReport.setForeground(new Color(72,131,184));
        JMenuItem StayRequest = new JMenuItem("Prolog Dormitary stay request");
        StayRequest.setForeground(new Color(72,131,184));
        JMenuItem RequestForDorm = new JMenuItem("Request for a dorm");
        RequestForDorm.setForeground(new Color(72,131,184));
        JMenuItem SeeRequests = new JMenuItem("See your requests");
        SeeRequests.setForeground(new Color(72,131,184));

        Service.add(maintainanceReport);
        Service.add(StayRequest);
        Service.add(RequestForDorm);
        Service.add(SeeRequests);

        Services.add(Service);

        this.setJMenuBar(Services);
        this.setVisible(true);
    }
}
