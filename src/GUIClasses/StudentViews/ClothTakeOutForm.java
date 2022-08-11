package GUIClasses.StudentViews;

import BasicClasses.Others.Cloth;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.ClothTakeOutRequest;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Date;

public class ClothTakeOutForm extends JFrame implements RequestViews {
    private JTextField clothName;
    private JTextField clothAmount;
    private JButton addButton;
    private JButton finishButton;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel titlePanel;
    private JTable clothTable;
    private JLabel descriptionLabel;
    private JScrollPane scrollPane;
    private ClothTakeOutRequest clothList;
    String reporterId = "yep it is"; // This part here is only for debugging. It will be removed when the project is complete.
    public final int WIDTH = 400;
    public final int HEIGHT = 200;

    public ClothTakeOutForm(){
        Integer lastRequestId = this.getLastClothRequestId();
        clothList = new ClothTakeOutRequest(reporterId,lastRequestId);
        setUpGUi();
        setUpTable();
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Student cloth Take Out Form");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void setUpTable(){
        Vector<String> title = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();
        Vector<Cloth> cloths = clothList.getClothsList();

        for(Cloth c : cloths ){
            Vector<Object> tmp = new Vector<>();
            tmp.add(c.getClothName());
            tmp.add(c.getClothAmount());
            data.add(tmp);
        }

        title.add("Cloth Name");
        title.add("Amount");
        clothTable.setModel(new DefaultTableModel(data,title));
    }

    public ClothTakeOutRequest getClothTable() {
        return clothList;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Cloth getClothInfo() throws NumberFormatException{
        String name = clothName.getText();
        int amount = Integer.parseInt(clothAmount.getText());
        return new Cloth(name,amount);
    }
    public void addClothToView(Cloth cloth){
        this.revalidate();
    }
    public void clear(){
        clothName.setText("");
        clothAmount.setText("");
    }

    public Integer updateDataBase() {
        String url = "jdbc:sqlserver://DESKTOP-AA4PR2S\\SQLEXPRESS;DatabaseName=DMS;" +
                "encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;";
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        String reportType = this.getTitle();
        JavaConnection javaConnection = new JavaConnection(url);
        Integer updateStatus = 0;
        for (Cloth c : clothList.getClothsList()) {
            String query = "INSERT INTO clothRequest(requestID,reporterID,ClothName,clothAmount,reportedDate)" +
                            "VALUES(\'" +clothList.getRequestId()+"\',\'"+ reporterId + "\',\'" + c.getClothName()+ "\',\'" +
                    c.getClothAmount()+"\',\'"+ date + "\');";
            if (javaConnection.isConnected()) updateStatus = javaConnection.insertQuery(query);
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
        String url = "jdbc:sqlserver://DESKTOP-AA4PR2S\\SQLEXPRESS;DatabaseName=DMS;" +
                "encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;";
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
