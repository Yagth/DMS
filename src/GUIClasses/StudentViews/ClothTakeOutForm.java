package GUIClasses.StudentViews;

import BasicClasses.Others.Cloth;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.ClothTakeOutRequest;
import GUIClasses.ActionListeners.ClothTakeOutAddButtonListener;
import GUIClasses.ActionListeners.ClothTakeOutFinishButtonListener;
import GUIClasses.Interfaces.RequestViews;
import GUIClasses.Interfaces.TableViews;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Date;

public class ClothTakeOutForm extends JFrame implements RequestViews, TableViews {
    private JButton addButton;
    private JButton finishButton;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel titlePanel;
    private JTable clothTable;
    private JLabel descriptionLabel;
    private JScrollPane scrollPane;
    private JPanel userInputPanel;
    private JTextField clothNameTF;
    private JTextField clothAmountTF;
    private JLabel clothNameL;
    private JLabel clothAmountL;
    private ClothTakeOutRequest clothList;
    private Vector<Vector<Object>> tableData;
    private Student student;
    public final int WIDTH = 500;
    public final int HEIGHT = 300;

    public ClothTakeOutForm(Student student){
        Integer lastRequestId = this.getLastClothRequestId();
        clothList = new ClothTakeOutRequest(student.getsId(),lastRequestId);
        tableData = new Vector<>();
        this.student = student;
        setUpGUi();
        setUpTable();
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Student cloth Take Out Form");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.addButton.addActionListener(new ClothTakeOutAddButtonListener(this));
        this.finishButton.addActionListener(new ClothTakeOutFinishButtonListener(this));
    }

    @Override
    public void setUpTable(){
        Vector<String> title = new Vector<>();

        title.add("No");
        title.add("Cloth Name");
        title.add("Amount");
        clothTable.setModel(new DefaultTableModel(tableData,title));
        clothTable.getColumn("No").setMaxWidth(50);
    }

    public ClothTakeOutRequest getClothRequest() {
        return clothList;
    }
    @Override
    public void addDataToTable(Object object){
        Cloth cloth = (Cloth) object;
        Vector<Object> tmp = new Vector<>();
        tmp.add(clothList.getClothsList().size());
        tmp.add(cloth.getClothName());
        tmp.add(cloth.getClothAmount());
        tableData.add(tmp);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Cloth getClothInfo() throws NumberFormatException{
        String name = clothNameTF.getText();
        int amount = Integer.parseInt(clothAmountTF.getText());
        return new Cloth(name,amount);
    }

    public JTextField getClothAmountTF() {
        return clothAmountTF;
    }

    public JTextField getClothNameTF() {
        return clothNameTF;
    }

    @Override
    public void refreshTable(){
       DefaultTableModel tableModel = (DefaultTableModel) clothTable.getModel();
       tableModel.fireTableDataChanged();
    }

    public void clear(){
        clothNameTF.setText("");
        clothAmountTF.setText("");
    }

    public Integer updateDataBase() {
        String url = JavaConnection.URL;
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        String reportType = this.getTitle();
        JavaConnection javaConnection = new JavaConnection(url);
        Integer updateStatus = 0;
        int tmp1,tmp2;

        for (Cloth c : clothList.getClothsList()) {
            String query = "INSERT INTO ClothTakeOut(ReportId,ReporterID,ClothName,Amount)" +
                            "VALUES(\'" +clothList.getRequestId()+"\',\'"+ clothList.getRequesterId() + "\',\'" + c.getClothName()+ "\',\'" +
                    c.getClothAmount()+"\');";
            String query2 = "INSERT INTO StudentTakesClothOut(ReporterId,clothRequestId,reportedDate)" +
                    "VALUES(\'" +student.getsId()+"\',\'"+ clothList.getRequestId() + "\',\'" + date+ "\');";
            if (javaConnection.isConnected()){
                tmp1 = javaConnection.insertQuery(query);
                tmp2 = javaConnection.insertQuery(query2);
                if(tmp1 == 1 & tmp2 == 1) updateStatus = 1;//Both queries are executed. JavaConnection returns 1 if successful and 0 otherwise.
                else updateStatus = 0;
            }
        }
        return updateStatus;
    }
    public void displayUpdateStatus(Integer updateStatus){
        if (updateStatus.equals(1))
            JOptionPane.showMessageDialog(null, "Request sent successfully", "Message sent", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Sorry couldn't send your request due to connection error", "Connection error", JOptionPane.ERROR_MESSAGE);
    }
    public Integer getLastClothRequestId(){
        int lastRequestId = 0;
        String url = JavaConnection.URL;
        JavaConnection javaConnection = new JavaConnection(url);
        String query = "SELECT TOP 1 * FROM clothRequest ORDER BY requestId DESC, clothName DESC;";
        ResultSet resultSet = javaConnection.selectQuery(query);
        try{
            resultSet.next();
            String tmp = resultSet.getString("requestId");
            lastRequestId = Integer.parseInt(tmp);
            return lastRequestId;
        }catch(SQLException ex){
            return 0;
        }
    }
    public boolean checkSimilarNameCloth(Cloth cloth){
        for(Cloth c: clothList.getClothsList()){
            return (c.getClothName().equalsIgnoreCase(cloth.getClothName()));
        }
        return false;
    }
}
