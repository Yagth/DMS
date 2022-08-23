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
import java.sql.SQLOutput;
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
    }
    public DormitoryDetailView(){
        this(null,null,null);
    }
    private Vector<Vector<Object>> loadStudents(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        Vector<Vector<Object>> tmp =null;
        System.out.println("In loadStudents");//For debugging only
        if(javaConnection.isConnected()){
            System.out.println("In if condition");//For debugging only
            tmp = new Vector<>();
            String query = "SELECT SID, Fname,Lname,Year,isEligible FROM Student " +
                    "WHERE BuildingNumber='"+buildingNoL.getText()+"' AND RoomNumber='"+roomNoL.getText()+"'";
            System.out.println("Query: "+query);
            ResultSet resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    System.out.println("in while loop");//For debugging only
                    Vector<Object> temp = new Vector<>();
                    temp.add(resultSet.getString("Fname")+resultSet.getString("Lname"));
                    System.out.println("Fname: "+resultSet.getString("Fname"));//For debugging only.
                    temp.add(resultSet.getString("SID"));
                    temp.add(resultSet.getInt("Year"));
                    temp.add(resultSet.getBoolean("isEligible"));
                    tmp.add(temp);
                }
                System.out.println("finished while loop");//For debugging only
                System.out.println("Is tmp empty: "+tmp.isEmpty());//For debugging only
                System.out.println("Is tmp null: "+tmp==null);//For debugging only

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
        studentList.getColumn("ID").setMaxWidth(100);

    }

    @Override
    public void addDataToTable(Object object) {
        Vector<Vector<Object>> tmp = ( Vector<Vector<Object>>) object;
        tableData = tmp;
        for (Vector<Object> c : tableData ){//For debugging only
            for(Object o : c){
                System.out.print(o);
            }
            System.out.println();
        }
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
        displayReadStatus(readStatus);

        this.setVisible(true);
    }
}
