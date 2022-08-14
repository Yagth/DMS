package GUIClasses.StudentViews;

import BasicClasses.Others.JavaConnection;
import GUIClasses.ActionListeners.RequestForDormitorySubmitButtonListener;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.sql.Date;
import java.util.Calendar;

public class RequestForDormitory extends JFrame implements RequestViews {
    private JLabel titleLabel;
    private JTextField subcityTF;
    private JLabel addressLabel;
    private JPanel inputPanel;
    private JLabel subcityLabel;
    private JTextField woredaTF;
    private JLabel woredaLabel;
    private JTextPane descriptionPane;
    private JScrollPane descriptionSP;
    private JLabel descriptionLabel;
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JButton submitButton;
    private int reporterId = 4556; //This part is in only for debugging purpose.
    private static final int WIDTH = 600;
    private static final int HEIGHT = 250;


    public RequestForDormitory(){
        setUpGUi();
    }
    public String getSubcity(){
        return this.subcityTF.getText();
    }
    public String getWoreda(){
        return this.woredaTF.getText();
    }
    public String getDescription(){
        return this.descriptionPane.getText();
    }

    @Override
    public Integer updateDataBase() {
        String url = "jdbc:sqlserver://DESKTOP-AA4PR2S\\SQLEXPRESS;DatabaseName=DMS;" +
                "encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;";
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        String reportType = this.getTitle();
        JavaConnection javaConnection = new JavaConnection(url);
        Integer updateStatus = 0;
        String query = "INSERT INTO request(reportedID,requestType,roomNO,blockNo,reportDate,description)" +
                "VALUES(\'" +reporterId+"\',\'"+ reportType + "\',\'" + getWoreda()+ "\',\'" +
                getSubcity()+"\',\'"+ date + "\',\'"+getDescription()+"\');";
        if (javaConnection.isConnected()) updateStatus = javaConnection.insertQuery(query);
        return updateStatus;

    }

    @Override
    public void displayUpdateStatus(Integer updateStatus) {
        if (updateStatus.equals(1))
            JOptionPane.showMessageDialog(null, "Request sent successfully", "Message sent", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Sorry couldn't send your request due to connection error", "Connection error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setUpGUi() {
        this.setContentPane(mainPanel);
        this.setTitle("RequestForDormitory");
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        submitButton.addActionListener(new RequestForDormitorySubmitButtonListener(this));
    }
}
