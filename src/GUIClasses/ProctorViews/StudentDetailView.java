package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Persons.Student;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.text.View;

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
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public StudentDetailView(Student student, StudentView parentComponent){
        this.student = student;
        this.parentComponent = parentComponent;
        setUpGUi();

    }
    public StudentDetailView(){this(null,null);}//This constructor is only for debugging purposes.

    @Override
    public void setUpTable() {

    }

    @Override
    public void addDataToTable(Object object) {

    }

    @Override
    public void refreshTable() {

    }

    @Override
    public void setUpGUi() {
        this.setTitle("Student detail view");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
