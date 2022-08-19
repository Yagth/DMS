package GUIClasses.StudentViews;

import BasicClasses.Enums.ConnectionParameters;
import BasicClasses.Others.JavaConnection;
import GUIClasses.ActionListeners.MaintenanceSubmitButtonListener;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.sql.Date;
import java.util.Calendar;

public class MaintenanceRequestForm extends JFrame implements RequestViews {
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
    private JScrollPane descriptionSP;
    String reporterId = "yep it is"; // This part here is only for debugging. It will be removed when the project is complete.
    public final int WIDTH = 500;
    public final int HEIGHT = 230;


    public MaintenanceRequestForm(){
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
        submitButton.addActionListener(new MaintenanceSubmitButtonListener(this));

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
        String url = ConnectionParameters.URL;
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        String reportType = this.getTitle();
        JavaConnection javaConnection = new JavaConnection(url);
        Integer updateStatus = 0;
        String query = "INSERT INTO request(reportedID,requestType,roomNO,blockNo,reportDate,description)" +
                "VALUES(\'" +reporterId+"\',\'"+ reportType + "\',\'" + getRoomNumber()+ "\',\'" +
                getBlockNumber()+"\',\'"+ date + "\',\'"+getDescription()+"\');";
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
