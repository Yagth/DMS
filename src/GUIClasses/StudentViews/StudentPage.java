package GUIClasses.StudentViews;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import GUIClasses.ActionListeners.StudentPage.*;
import GUIClasses.Interfaces.TableViews;

public class StudentPage extends JFrame implements TableViews {
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
    private Vector<Vector<String>> tableData;
    private static final SizeOfMajorClasses WIDTH = SizeOfMajorClasses.WIDTH;
    private static final SizeOfMajorClasses HEIGHT = SizeOfMajorClasses.HEIGHT;


    public StudentPage(Student student){
        user = student;
        setUpGUi();
        setUpTable();
        displayUserInfo();
        addDormMatesToView();
    }

    public StudentPage(){  //This constructor is only here for debugging purposes.
        setUpGUi();
    } // This constructor is only for debugging purposes.

    public void loadDormMates(){
        String url = JavaConnection.URL;
        String query = "SELECT Fname, Lname, PhoneNumber FROM Student as S, Student_Phonenumber as SP WHERE S.SID = SP.SID AND BuildingNumber=\'"
                +user.getBuildingNo()+"\' AND RoomNumber=\'"+user.getDormNo()+"\' AND S.SID != \'"+user.getsId()+"\'";
        JavaConnection javaConnection = new JavaConnection(url);
        dormMates = javaConnection.selectQuery(query);
    }

    public void displayUserInfo(){
        studentName.setText(user.getFullName());
        studentID.setText(user.getsId());
        studentBuildingNo.setText(String.valueOf(user.getBuildingNo()));
        studentDormNo.setText(String.valueOf(user.getDormNo()));
    }

    public void addDormMatesToView(){
        loadDormMates();
        addDataToTable(dormMates);
        refreshTable();
    }
    public Student getUser(){
        return user;
    }

    @Override
    public void setUpTable(){
        Vector<String> title = new Vector<>();
        tableData = new Vector<>();

        title.add("Name");
        title.add("Phone Number");
        dormMateTable.setModel(new DefaultTableModel(tableData,title));
        dormMateTable.setDefaultEditor(Object.class, null); // This code here will disable the editing of the table.
    }

    @Override
    public void addDataToTable(Object object) {
        ResultSet resultSet = (ResultSet) object;
        try{
            while(resultSet.next()){
                Vector<String> tmp = new Vector<>();
                tmp.add(resultSet.getString("Fname")+" "+resultSet.getString("Lname"));
                tmp.add(resultSet.getString("Phonenumber"));
                tableData.add(tmp);
            }
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(this,"Couldn't find dorm mates due to connection error",
                    "Connection error",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // This part is only here for debugging purposes.
        }
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) dormMateTable.getModel();
        tableModel.fireTableDataChanged();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("My Dormitory");
        this.setContentPane(MainPanel);
        this.setSize(WIDTH.getSize(), HEIGHT.getSize());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JMenuBar Services = new JMenuBar();
        Services.setBackground(new Color(72,131,184));
        JMenu Service = new JMenu("Services");
        Service.setForeground(Color.white);

        JMenuItem maintainanceRequest = new JMenuItem("Maintainance request");
        maintainanceRequest.setForeground(new Color(72,131,184));
        maintainanceRequest.addActionListener(new MaintenanceMenuItemListener(this));
        JMenuItem clothTakeOutRequest = new JMenuItem("Cloth take out request");
        clothTakeOutRequest.setForeground(new Color(72,131,184));
        clothTakeOutRequest.addActionListener(new ClothTakeOutMenuItemListener(this));
        JMenuItem StayRequest = new JMenuItem("Extend Dormitory stay request");
        StayRequest.setForeground(new Color(72,131,184));
        StayRequest.addActionListener(new ExtendDormMenuItemListener(this));
        JMenuItem RequestForDorm = new JMenuItem("Request for a dorm");
        RequestForDorm.setForeground(new Color(72,131,184));
        RequestForDorm.addActionListener(new RequestForDormMenuItemListener(this));
        JMenuItem SeeRequests = new JMenuItem("See your requests");
        SeeRequests.setForeground(new Color(72,131,184));

        Service.add(maintainanceRequest);
        Service.add(clothTakeOutRequest);
        Service.add(StayRequest);
        Service.add(RequestForDorm);
        Service.add(SeeRequests);

        Services.add(Service);

        JMenu logout = new JMenu("Logout");
        logout.setForeground(Color.white);
        JMenuItem signOut = new JMenuItem("Logout");
        signOut.setForeground(new Color(72,131,184));
        signOut.addActionListener(new LogoutMenuItemListener(this));

        logout.add(signOut);
        Services.add(logout);

        this.setJMenuBar(Services);
        this.setVisible(true);
    }

}
