package GUIClasses.StudentViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import GUIClasses.Interfaces.RequestViews;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class SeeYourRequests extends JFrame implements Views, TableViews {
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
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public SeeYourRequests(Student student, StudentPage parentComponent){
        this.parentComponent = parentComponent;
        this.student = student;
        javaConnection = new JavaConnection(JavaConnection.URL);
        setUpGUi();
        setUpTable();
    }
    @Override
    public void setUpTable() {
        tableData = new Vector<>();
        Vector<String> title = new Vector<>();
        title.add("Report Id");
        title.add("Report Type");
        title.add("Reported Date");
        title.add("Report Description");

        reportListTable.setModel(new DefaultTableModel(tableData,title));
        reportListTable.getColumn("Report Id").setMaxWidth(20);
        reportListTable.getColumn("Report Type").setMaxWidth(60);
        reportListTable.getColumn("Report Date").setMaxWidth(60);
    }

    @Override
    public void addDataToTable(Object object) {

    }

    @Override
    public void refreshTable() {

    }

    @Override
    public void setUpGUi() {
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(parentComponent);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.
    }
}
