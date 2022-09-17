package GUIClasses.ProctorViews;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DormitoryDetailView extends JFrame implements Views, TableViews {
    private JPanel mainPanel;
    private JPanel dormInfoPanel;
    private JPanel studentListPanel;
    private JLabel buildingNoL;
    private JLabel bedNoL;
    private JLabel lockerNoL;
    private JLabel tableNoL;
    private JLabel chairsNoL;
    private JLabel keyHolderL;
    private JScrollPane studentListSP;
    private JTable studentList;
    private JButton backButton;
    private JLabel roomNoL;
    private DormitoryView parentComponent;
    private Proctor proctor;
    private Dormitory dorm;
    private Vector<Vector<Object>> tableData;
    private boolean readStatus;
    public DormitoryDetailView(Dormitory dorm,Proctor proctor,DormitoryView parentComponent){
        this.parentComponent = parentComponent;
        this.dorm = dorm;
        this.proctor = proctor;
        setUpGUi();
        displayReadStatus(readStatus);
    }
    public DormitoryDetailView(){
        this(null,null,null);
    }//For debugging only constructor.
    private Vector<Vector<Object>> loadStudents(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        Vector<Vector<Object>> tmp =null;
        if(javaConnection.isConnected()){
            tmp = new Vector<>();
            String query = "SELECT SID, Fname,Lname,Year,isEligible FROM Student " +
                    "WHERE BuildingNumber='"+buildingNoL.getText()+"' AND RoomNumber='"+roomNoL.getText()+"'";
            ResultSet resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    Vector<Object> temp = new Vector<>();
                    temp.add(resultSet.getString("Fname")+resultSet.getString("Lname"));
                    temp.add(resultSet.getString("SID"));
                    temp.add(resultSet.getInt("Year"));
                    temp.add(resultSet.getBoolean("isEligible"));
                    tmp.add(temp);
                }

            } catch (SQLException ex){
                String message = "Error encountered while reading students data from server.";
                displayReadStatus(false,message);
            }
        }
        return tmp;
    }
    private void loadDormInfo(){
        buildingNoL.setText(dorm.getBuildingNo());
        roomNoL.setText(dorm.getRoomNO());
        bedNoL.setText(String.valueOf(dorm.getNoOfBeds()));
        lockerNoL.setText(String.valueOf(dorm.getNoOfLockers()));
        chairsNoL.setText(String.valueOf(dorm.getNoOfChairs()));
        tableNoL.setText(String.valueOf(dorm.getNoOfTables()));
        keyHolderL.setText(dorm.getKeyHolderId());
    }
    public void displayReadStatus(boolean readStatus){
        String message = "Couldn't read data from server due to connection error ";
        if(readStatus) displayReadStatus(true,message);
        else displayReadStatus(false,message);
    }
    public void displayReadStatus(boolean readStatus, String message){
        if(readStatus)
            JOptionPane.showMessageDialog(this,"Read Successful");
        else{
            JOptionPane.showMessageDialog(this,message);
        }
    }
    public void showParentComponent(){
        parentComponent.setVisible(true);
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
        titles.add("Student name");
        titles.add("ID");
        titles.add("Year");
        titles.add("Eligibility");

        Vector<Vector<Object>> tmp = loadStudents();
        readStatus = !(tmp == null);
        addDataToTable(tmp);

        studentList.setModel(new DefaultTableModel(tableData, titles));
        studentList.setDefaultEditor(Object.class, null);
        studentList.getColumn("Year").setMaxWidth(50);

    }

    @Override
    public void addDataToTable(Object object) {
        Vector<Vector<Object>> tmp = ( Vector<Vector<Object>>) object;
        tableData = tmp;
        refreshTable();
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) studentList.getModel();
        tableModel.fireTableDataChanged();
    }

    @Override
    public void setUpGUi() {
        this.setContentPane(mainPanel);
        this.setTitle("Detail Info of the dorm");
        this.setSize(600,700);
        this.setLocationRelativeTo(parentComponent);
        loadDormInfo();
        setUpTable();

        this.setVisible(true);
    }
}
