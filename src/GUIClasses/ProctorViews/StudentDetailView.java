package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import GUIClasses.ActionListeners.ProctorView.StudentView.BackButtonListener;
import GUIClasses.ActionListeners.ProctorView.StudentView.DeallocateButtonListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StudentDetailView extends JFrame implements Views, TableViews {
    private JPanel mainPanel;
    private JPanel studentInfoPanel;
    private JPanel buttonPanel;
    private JPanel emergencyContactPanel;
    private JTable emergencyContactList;
    private JLabel studentNameL;
    private JLabel idL;
    private JLabel buildingNumberL;
    private JLabel dormNumberL;
    private JLabel eligibilityL;
    private JLabel departmentL;
    private JLabel equipmentsL;
    private JButton deallocateDormButton;
    private JButton backButton;
    private JLabel placeL;
    private JLabel phoneNumberL;
    private JLabel yearL;
    private JLabel genderL;
    private Student student;
    private StudentView parentComponent;
    private Vector<Vector<Object>> tableData;
    private boolean readStatus;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public StudentDetailView(Student student, StudentView parentComponent){
        this.student = student;
        this.parentComponent = parentComponent;
        setUpGUi();

    }

    public String getStudentID(){
        return student.getsId();
    }
    public void displayStudentInfo(){
        /*
        This method will display all the student info in the labels except for the emergency contact info
        which will need to be displayed in the table since it contains more field than just names.
         */
        studentNameL.setText(student.getFullName());
        idL.setText(student.getsId());
        buildingNumberL.setText(String.valueOf(student.getBuildingNo()));
        dormNumberL.setText(String.valueOf(student.getDormNo()));
        placeL.setText(student.getPlaceOfOrigin());
        yearL.setText(String.valueOf(student.getYear()));
        departmentL.setText(student.getDepartment());
        phoneNumberL.setText(student.getPhoneNumber());
        genderL.setText(student.getGender());

        if(student.getEligibility()) eligibilityL.setText("Is Eligible");
        else eligibilityL.setText("Is NOT Eligible");

        if(student.getHasAllEquipments()) equipmentsL.setText("Has all equipments");
        else equipmentsL.setText("Doesn't have all equipments");
    }

    public Vector<Vector<Object>> loadEmergencyContacts(){
        /*
        This method will load every emergency contacts from the database and add them in the tableData
        vector accordingly.
         */
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM EmergencyContact WHERE SID='"+student.getsId()+"'";
        ResultSet resultSet;
        Vector<Vector<Object>> contacts = new Vector<>();

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    Vector<Object> tmp = new Vector<>();
                    tmp.add(resultSet.getString("Fname")+" "+resultSet.getString("Lname"));
                    tmp.add(resultSet.getString("PhoneNumber"));
                    tmp.add(resultSet.getString("Place"));
                    tmp.add(resultSet.getString("Relation"));
                    contacts.add(tmp);

                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return contacts;
    }

    public void displayReadStatus(boolean readStatus){
        if(!readStatus)
            JOptionPane.showMessageDialog(this,"Couldn't load the emergency contacts"
                    ,"Reading Error",JOptionPane.INFORMATION_MESSAGE);
    }

    public void goBackToParent(){
        this.dispose();
        parentComponent.setVisible(true);
    }

    public void reloadParentTable(){
        parentComponent.reloadTable();
    }

    @Override
    public boolean nextButtonIsVisible() {
        return false;
    }

    @Override
    public boolean prevButtonIsVisible() {
        return false;
    }

    @Override
    public void setButtonVisibility() {

    }

    @Override
    public void reloadTable() {
    }

    @Override
    public void setUpTable() {
        Vector<String> titles = new Vector<>();
        tableData = new Vector<>();
        titles.add("Name");
        titles.add("Phone Number");
        titles.add("Place of Origin");
        titles.add("Relation to student");
        emergencyContactList.setModel(new DefaultTableModel(tableData,titles));
        emergencyContactList.setDefaultEditor(Object.class,null); // To make the table non editable.
        Vector<Vector<Object>> contacts = loadEmergencyContacts();
        readStatus = !(contacts.size()==0); //tableData will be null if the read isn't successful making readStatus false.
        addDataToTable(contacts);
        displayReadStatus(readStatus);
        refreshTable();
    }

    @Override
    public void addDataToTable(Object object) {
        Vector<Vector<Object>> contacts = (Vector<Vector<Object>>) object;
        for(Vector<Object> contact: contacts){
            tableData.add(contact);
        }
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) emergencyContactList.getModel();
        tableModel.fireTableDataChanged();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Student detail view");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);

        deallocateDormButton.addActionListener(new DeallocateButtonListener(this));
        backButton.addActionListener(new BackButtonListener(this));
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.
        setUpTable();
        displayStudentInfo();
        this.setVisible(true);
    }
}
