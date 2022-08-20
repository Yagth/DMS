package GUIClasses.StudentViews;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import GUIClasses.Interfaces.RequestViews;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.util.Vector;

public class SeeYourRequests implements Views, TableViews {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel studentInfoPanel;
    private JLabel nameL;
    private JLabel IDL;
    private JLabel studentName;
    private JLabel studentID;
    private JLabel studentBuildingNo;
    private JLabel buildingNoL;
    private JLabel studentDormNo;
    private JLabel dormNoL;
    private JTable reportListTable;
    private JScrollPane reportListSP;
    private JLabel titleL;
    private JavaConnection javaConnection;
    private Student student;
    private StudentPage parentComponent;
    private Vector<Vector<Object>> tableData;

    public SeeYourRequests(Student student, StudentPage parentComponent){
        this.parentComponent = parentComponent;
        this.student = student;
        javaConnection = new JavaConnection(JavaConnection.URL);
    }
    @Override
    public void setUpTable() {
        tableData = new Vector<>();
        Vector<String> title = new Vector<>();
        title.add("");

    }

    @Override
    public void addDataToTable(Object object) {

    }

    @Override
    public void refreshTable() {

    }

    @Override
    public void setUpGUi() {

    }
}
