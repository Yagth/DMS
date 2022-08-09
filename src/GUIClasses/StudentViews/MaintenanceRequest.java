package GUIClasses.StudentViews;

import BasicClasses.Others.JavaConnection;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.sql.Date;
import java.util.Calendar;

public class MaintenanceRequest extends JFrame implements RequestViews {
    private JPanel mainPanel;
    private JPanel innerPanel;
    private JLabel locationLabel;
    private JTextField blockNumberTextField;
    private JTextField roomNumberTextField;
    private JButton submitButton;
    private JLabel descriptionLabel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JLabel buildingNoLabel;
    private JLabel roomNoLabel;
    private JTextPane descriptionTextPane;
    String reporterId = "yep it is"; // This part here is only for debugging. It will be removed when the project is complete.
    public final int WIDTH = 500;
    public final int HEIGHT = 230;


    public MaintenanceRequest(){
       setUpGUi();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Maintenance Request");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    public int getBlockNumber(){
        int tmp;
        try{
            tmp = Integer.parseInt(blockNumberTextField.getText());
        }
        catch (NumberFormatException ex){
            tmp = 0;
        }
        return tmp ;
    }
    public int getRoomNumber(){
        int tmp;
        try{
            tmp = Integer.parseInt(roomNumberTextField.getText());
        }
        catch (NumberFormatException ex){
            tmp = 0;
        }
        return tmp ;
    }
    public String getDescription(){
        return descriptionTextPane.getText();
    }


    @Override
    public Integer updateDataBase() {
        String url = "jdbc:sqlserver://DESKTOP-AA4PR2S\\SQLEXPRESS;DatabaseName=DMS;" +
                "encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;";
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        String reportType = this.getTitle();
        JavaConnection javaConnection = new JavaConnection(url);
        Integer updateStatus = 0;
        String query = "INSERT INTO request(reporterID,requestType,roomNO,blockNo,reportedDate)" +
                "VALUES(\'" +reporterId+"\',\'"+ this.getTitle() + "\',\'" + getRoomNumber()+ "\',\'" +
                this.getBlockNumber()+"\',\'"+ date + "\');";
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
}
