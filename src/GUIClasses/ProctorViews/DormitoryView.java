package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ActionListeners.ProctorView.AllocateDormView.AllocateDormAsRequested;
import GUIClasses.ActionListeners.ProctorView.DormitoryView.*;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Vector;

public class DormitoryView extends JFrame implements Views, TableViews {
    private JFormattedTextField searchBuildingNoTA;
    private JButton searchButton;
    private JButton filterButton;
    private JPanel headingPanel;
    private JPanel mainPanel;
    private JLabel totalNoLabel;
    private JLabel totalSpaceNo;
    private JLabel emptyDormNoLabel;
    private JPanel totalDormInfoPanel;
    private JLabel numberOfDorm;
    private JPanel buttonPanel;
    private JLabel searchDormLabel;
    private JLabel filterLabel;
    private JComboBox filterList;
    private JLabel BNOLabel;
    private JFormattedTextField searchRoomNoTA;
    private JLabel RNOLabel;
    private JLabel backLabel;
    private JLabel previousPageLabel;
    private JLabel nextPageLabel;
    private JTextField yearTA;
    private JTable dormListTable;
    private JPanel dormLIstPanel;
    private ProctorPage parentComponent;
    private Proctor proctor;
    private Vector<Vector<Object>> tableData;
    private ArrayList<Dormitory> dorms;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public DormitoryView(Proctor proctor, ProctorPage parentComponent){
        this.proctor = proctor;
        this.parentComponent = parentComponent;
        dorms = new ArrayList<>();
        setUpGUi();
    }

    public void showParentComponent(){
        parentComponent.setVisible(true);
    }

    public JLabel getBackLabel() {
        return backLabel;
    }

    public Proctor getProctor() {
        return proctor;
    }

    public String getSelectedCondition(){
       return (String) filterList.getSelectedItem();
    }

    public void setYearTAVisibility(boolean visibility){
        yearTA.setVisible(visibility);
    }

    public int getYear(){
        int year = 0;
        try{
            year = Integer.parseInt(yearTA.getText());
        } catch (NumberFormatException ex){
            ex.printStackTrace();//For debugging only.
            JOptionPane.showMessageDialog(this,"Please enter the correct year",
                    "Invalid input",JOptionPane.ERROR_MESSAGE);
        }
        return year;
    }

    public void changeTableData(ArrayList<Dormitory> dorms){
        this.dorms = dorms;
        tableData.clear();
        addDataToTable(null);
        refreshTable();
    }
    public void loadDorms(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT D.BuildingNumber,D.RoomNumber, maxCapacity, COUNT(SID) AS NumberOfStudent " +
                        "FROM Dorm AS D LEFT JOIN Student AS S ON D.BuildingNumber = S.BuildingNumber " +
                        "AND D.RoomNumber = S.RoomNumber GROUP BY D.BuildingNumber,D.RoomNumber,maxCapacity";
        ResultSet resultSet;

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            Dormitory tmp;
            try{
                while(resultSet.next()){
                    tmp = new Dormitory(resultSet.getString("RoomNumber"),
                                        resultSet.getString("BuildingNumber"),
                                        resultSet.getInt("maxCapacity")
                    );
                    tmp.setNoOfStudents(resultSet.getInt("NumberOfStudent"));
                    dorms.add(tmp);
                }
            }catch (SQLException ex){
                ex.printStackTrace();//For debugging purpose.
            }
        }
        else displayUpdateStatus(false);
    }

    public void displayUpdateStatus(boolean updateStatus){
        if(!updateStatus)
            JOptionPane.showMessageDialog(parentComponent,"Couldn't dorms due to some error",
                    "Connection error",JOptionPane.ERROR_MESSAGE);
    }

    public int totalAvailableSpace(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT (maxCapacity-NumberOfStudents) AS AvailableSpace FROM AvailableDorm WHERE BuildingNumber='"+proctor.getBuildingNo()+"'";
        ResultSet resultSet;
        int totalSpace = 0;
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    totalSpace+=resultSet.getInt("AvailableSpace");
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return totalSpace;
    }

    public int emptyDorms(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT COUNT(RoomNumber) AS EmptyDorm FROM AvailableDorm WHERE NumberOfStudents=0 AND BuildingNumber='"+proctor.getBuildingNo()+"'";
        ResultSet resultSet;
        int emptyDorm = 0;
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    emptyDorm+=resultSet.getInt("EmptyDorm");
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return emptyDorm;
    }

    public String getBuildingNo(){
        return searchBuildingNoTA.getText();
    }

    public String getRoomNo(){
        return searchRoomNoTA.getText();
    }

    public void clearDorms(){
        dorms.clear();
        tableData.clear();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("DormitoryView");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        filterList.addItem("Year of Students");
        filterList.addItem("Available space");
        filterList.addItemListener(new FilterConditionItemListener(this));
        backLabel.addMouseListener(new BackLabelListener(this));
        totalSpaceNo.setText(String.valueOf(totalAvailableSpace()));
        numberOfDorm.setText(String.valueOf(emptyDorms()));


        ImageIcon searchButtonIcon = new ImageIcon("Icons/SearchIcon.png");
        searchButton.setIcon(searchButtonIcon);
        ImageIcon backButtonIcon = new ImageIcon("Icons/backIcon.png");
        backLabel.setIcon(backButtonIcon);
        ImageIcon nextButtonIcon = new ImageIcon("Icons/nextIcon(3).png");
        nextPageLabel.setIcon(nextButtonIcon);
        ImageIcon previousButtonIcon = new ImageIcon("Icons/previousIcon-20x20.png");
        previousPageLabel.setIcon(previousButtonIcon);
        ImageIcon filterButtonIcon = new ImageIcon("Icons/FilterIcon.png");
        filterButton.setIcon(filterButtonIcon);

        searchButton.addActionListener(new SearchButtonListener(this));
        filterButton.addActionListener(new FilterButtonListener(this));
        searchBuildingNoTA.addFocusListener(new FocusListenerForTF(this));
        searchRoomNoTA.addFocusListener(new FocusListenerForTF(this));

        JMenuBar menuBar = new JMenuBar();

        JMenu actions = new JMenu("Actions");

        JMenuItem allocate = new JMenuItem("Allocate Dorms");
        allocate.addActionListener(new AllocateDormAsRequested(this));
        JMenuItem deallocate = new JMenuItem("Deallocate all dorms");
        deallocate.addActionListener(new DeallocateMenuListener(this));
        JMenuItem change = new JMenuItem("Change Dorm");
        change.addActionListener(new ChangeMenuListener(this));

        actions.add(allocate);
        actions.add(deallocate);
        actions.add(change);

        menuBar.add(actions);
        setUpTable();
        loadDorms();
        addDataToTable(null);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    @Override
    public void setUpTable() {
        Vector<String> titles = new Vector<>();
        tableData = new Vector<>();
        titles.add("Building Number");
        titles.add("Room Number");
        titles.add("Number of students");

        dormListTable.setModel(new DefaultTableModel(tableData,titles));
        dormListTable.setDefaultEditor(Object.class,null);//To make all rows non-editable.
    }

    @Override
    public void addDataToTable(Object object) {
        for(Dormitory dorm: dorms){
            Vector<Object> tmp = new Vector<>();
            tmp.add(dorm.getBuildingNo());
            tmp.add(dorm.getRoomNO());
            tmp.add(dorm.getNoOfStudents());
            tableData.add(tmp);
        }
        refreshTable();
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) dormListTable.getModel();
        tableModel.fireTableDataChanged();
    }
}
