package GUIClasses.StudentViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import GUIClasses.ActionListeners.NextActionListener;
import GUIClasses.ActionListeners.PrevActionListener;
import GUIClasses.ActionListeners.StudentView.SeeYourRequestBackButtonListener;
import GUIClasses.ActionListeners.StudentView.StudentPage.RequestDetailClickListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;
import GUIClasses.TableViewPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SeeYourRequests extends TableViewPage implements Views, TableViews {
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
    private JButton backButton;
    private JButton prevButton;
    private JButton nextButton;
    private JPanel buttonPanel;
    private JavaConnection javaConnection;
    private Student student;
    private StudentPage parentComponent;
    private Vector<Vector<Object>> tableData;
    ResultSet reports;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public SeeYourRequests(Student student, StudentPage parentComponent){
        this.parentComponent = parentComponent;
        this.student = student;
        javaConnection = new JavaConnection(JavaConnection.URL);

        String query = "SELECT Count(*) AS TotalNo FROM StudentReport WHERE reporterId='"+student.getsId()+"'";
        int totalPage = loadPageSize(query);
        setTotalSize(totalPage);


        setUpGUi();
        setUpTable();
        loadRequests();
        refreshTable();
        emptyReportDisplay();
    }
    public StudentPage getParentComponent(){return parentComponent;}
    public void showParentComponent(){
        this.getParentComponent().setVisible(true);
    }

    public JTable getReportListTable() {
        return reportListTable;
    }

    public Student getStudent() {
        return student;
    }

    public Vector<Vector<Object>> getTableData() {
        return tableData;
    }

    public void displayUserInfo(){
        studentName.setText(student.getFullName());
        studentID.setText(student.getsId());
        studentBuildingNo.setText(String.valueOf(student.getBuildingNo()));
        studentDormNo.setText(String.valueOf(student.getDormNo()));
    }
    public void emptyReportDisplay(){
        if(tableData.size() == 0)
            JOptionPane.showMessageDialog(this,"No reports Made so far",
                    "Empty Report",JOptionPane.INFORMATION_MESSAGE);
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
        reportListTable.setDefaultEditor(Object.class,null); //This part is here for disabling the editing of the table.
        reportListTable.addMouseListener(new RequestDetailClickListener(this));
    }


    @Override
    public boolean nextButtonIsVisible() {
        boolean hasNextPage = getPageNumber() < getTotalPage();
        return hasNextPage;
    }

    @Override
    public boolean prevButtonIsVisible() {
        boolean hasPrevPage = getPageNumber()>1;
        return hasPrevPage;
    }

    @Override
    public void setButtonVisibility() {
        boolean visibility = nextButtonIsVisible();
        nextButton.setVisible(visibility);
        visibility = prevButtonIsVisible();
        prevButton.setVisible(visibility);
        this.revalidate();
    }

    @Override
    public void reloadTable() {
        tableData.clear();
        loadRequests();
        refreshTable();
    }

    public void loadRequests(){
        String query = "SELECT * FROM StudentReport WHERE reporterId='"+student.getsId()+
                "' ORDER BY reportedDate DESC OFFSET "+((getPageNumber()-1)*ROW_PER_PAGE)+" ROWS"+
                " FETCH NEXT "+ROW_PER_PAGE+" ROWS ONLY";
        reports = javaConnection.selectQuery(query);
        try{
            while(reports.next()){
                Vector<Object> tmp = new Vector<>();
                tmp.add(reports.getInt("reportId"));
                tmp.add(reports.getString("ReportType"));
                tmp.add(reports.getString("ReportedDate"));
                tmp.add(reports.getString("description"));
                addDataToTable(tmp);
            }
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(this,"No reports Made so far",
                    "Empty Report",JOptionPane.INFORMATION_MESSAGE);
            parentComponent.dispose();
            showParentComponent();
            ex.printStackTrace(); // For debugging purposes.
        }
    }
    @Override
    public void addDataToTable(Object object) {
        Vector<Object> tmp = (Vector<Object>) object;
        tableData.add(tmp);
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) reportListTable.getModel();
        tableModel.fireTableDataChanged();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Your Requests");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(parentComponent);

        backButton.addActionListener(new SeeYourRequestBackButtonListener(this));
        nextButton.addActionListener(new NextActionListener(this));
        prevButton.addActionListener(new PrevActionListener(this));

        setButtonVisibility();

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.
        displayUserInfo();
        this.setVisible(true);
    }
}
