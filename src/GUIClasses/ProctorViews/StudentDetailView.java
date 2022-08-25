package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Persons.Student;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;
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
    public StudentDetailView(){this(null,null);}//This constructor is only for debugging purposes.
    public void displayStudentInfo(){
        /*
        This method will display all the student info in the labels except for the emergency contact info
        which will need to be displayed in the table since it contains more field than just names.
         */
    }

    public void loadEmergencyContacts(){
        /*
        This method will load every emergency contacts from the database and add them in the tableData
        vector accordingly.
         */
    }

    public void displayReadStatus(boolean readStatus){
        if(!readStatus)
            JOptionPane.showMessageDialog(this,"Couldn't read the emergency contacts due to connection error"
                    ,"Reading Error",JOptionPane.ERROR_MESSAGE);
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
        loadEmergencyContacts();
        readStatus = !(tableData == null); //tableData will be null if the read isn't successful making readStatus false.
        displayReadStatus(readStatus);
        refreshTable();
    }

    @Override
    public void addDataToTable(Object object) {
        /*
        No need to implement this method.
         */
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
        setUpTable();
        this.setVisible(true);
    }
}
