package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.ProctorView.StudentView.*;
import GUIClasses.ActionListeners.StudentView.BackButtonListener;
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

public class StudentView extends TableViewPage implements Views, TableViews {
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JFormattedTextField searchTF;
    private JButton searchButton;
    private JComboBox filterCondition;
    private JButton filterButton;
    private JPanel studentListPanel;
    private JTable studentListTable;
    private JPanel buttonPanel;
    private JButton nextButton;
    private JButton prevButton;
    private JButton backButton;
    private JTextField buildingNumberTF;
    private JLabel buildingNumberL;
    private ProctorPage parentComponent;
    private Proctor proctor;
    private Vector<Vector<Object>> tableData;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public StudentView(ProctorPage parentComponent,Proctor proctor){
        this.parentComponent = parentComponent;
        this.proctor = proctor;

        String query = "SELECT Count(*) TotalNo FROM Student";
        loadAndSetTotalPage(query);

        setUpGUi();
    }
    public void showParentComponent(){
        parentComponent.setVisible(true);
    }

    public Vector<Vector<Object>> loadStudents(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM Student";
        ResultSet resultSet;
        Vector<Vector<Object>> students = new Vector<>();
        int count = 0;
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    Vector<Object> tmp = new Vector<>();
                    tmp.add(++count);
                    tmp.add(resultSet.getString("SID"));
                    tmp.add(resultSet.getString("Fname")+" "+resultSet.getString("Lname"));
                    tmp.add(resultSet.getInt("Year"));
                    tmp.add(resultSet.getString("BuildingNumber"));
                    tmp.add(resultSet.getInt("isEligible"));

                    students.add(tmp);
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return students;
    }

    public Vector<Vector<Object>> loadStudents(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet;
        Vector<Vector<Object>> students = new Vector<>();
        int count = 0;
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    Vector<Object> tmp = new Vector<>();
                    tmp.add(++count);
                    tmp.add(resultSet.getString("SID"));
                    tmp.add(resultSet.getString("Fname")+" "+resultSet.getString("Lname"));
                    tmp.add(resultSet.getInt("Year"));
                    tmp.add(resultSet.getString("BuildingNumber"));
                    tmp.add(resultSet.getInt("isEligible"));

                    students.add(tmp);
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return students;
    }

    public void reloadTable(){
        tableData.clear();
        Vector<Vector<Object>> students = loadStudents();
        addDataToTable(students);
        refreshTable();
    }

    public void displayReadStatus(boolean readStatus){
        if(!readStatus) JOptionPane.showMessageDialog(parentComponent,"Couldn't load students due to " +
                "connection error","Loading error",JOptionPane.ERROR_MESSAGE);
    }

    public String getSelectedCondition(){
        return (String) filterCondition.getSelectedItem();
    }

    public String getSearchText(){
        return searchTF.getText();
    }

    public void setBuildingInputVisibility(boolean visibility){
        buildingNumberL.setVisible(visibility);
        buildingNumberTF.setVisible(visibility);
    }

    public void changeTextForFilterInput(String text){
        buildingNumberL.setText("");
        buildingNumberL.setText(text);
        buildingNumberTF.setToolTipText("Enter "+text+" here");
    }

    public String getFilterInputText(){
        return buildingNumberTF.getText();
    }
    public void clearInputTexts(){
        buildingNumberTF.setText("");
        searchTF.setText("");
    }

    public JTable getTable(){
        return this.studentListTable;
    }

    public Vector<Vector<Object>> getTableData(){
        return tableData;
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
    public void setUpTable() {
        Vector<String> titles = new Vector();
        tableData = new Vector<>();


        boolean readStatus;

        titles.add("No");
        titles.add("ID");
        titles.add("Name");
        titles.add("Year");
        titles.add("BuildingNumber");
        titles.add("Eligibility");

        Vector<Vector<Object>> students = loadStudents();

        readStatus = !(students.size() == 0);//It will be false if the students list is null.
        addDataToTable(students);

        studentListTable.setModel(new DefaultTableModel(tableData,titles));
        studentListTable.addMouseListener(new StudentDetailClickListener(this));
        studentListTable.setDefaultEditor(Object.class,null);
        studentListTable.getColumn("No").setMaxWidth(50);

        displayReadStatus(readStatus);
    }

    @Override
    public void addDataToTable(Object object) {
        Vector<Vector<Object>> students = (Vector<Vector<Object>>) object;
        for(Vector<Object> student : students){
            tableData.add(student);
        }
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) studentListTable.getModel();
        tableModel.fireTableDataChanged();
    }

    @Override
    public void setUpGUi() {
        setTitle("Students View");
        setContentPane(mainPanel);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);

        backButton.addActionListener(new BackButtonListener(this));
        searchButton.addActionListener(new SearchButtonListener(this));
        filterButton.addActionListener(new FilterButtonListener(this));
        filterCondition.addItemListener(new FilterConditionItemListener(this));
        searchTF.addFocusListener(new SearchTFFocusListener(this));


        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        ImageIcon searchButtonIcon = new ImageIcon("Icons/SearchIcon.png");
        searchButton.setIcon(searchButtonIcon);
        ImageIcon filterButtonIcon = new ImageIcon("Icons/FilterIcon.png");
        filterButton.setIcon(filterButtonIcon);

        filterCondition.addItem("");
        filterCondition.addItem("Year of students");
        filterCondition.addItem("Block");
        filterCondition.addItem("Eligibility");

        setUpTable();

        this.setVisible(true);
    }
}
