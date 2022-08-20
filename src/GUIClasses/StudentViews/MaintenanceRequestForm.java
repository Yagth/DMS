package GUIClasses.StudentViews;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.MaintenanceRequest;
import GUIClasses.ActionListeners.MaintenanceSubmitButtonListener;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private Student student;
    private StudentPage parentComponent;
    String reporterId = "yep it is"; // This part here is only for debugging. It will be removed when the project is complete.
    public final int WIDTH = 500;
    public final int HEIGHT = 230;


    public MaintenanceRequestForm(Student student, StudentPage parentComponent){
        this.student = student;
        this.parentComponent = parentComponent;
        setUpGUi();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Maintenance Request");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        submitButton.addActionListener(new MaintenanceSubmitButtonListener(this));
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

    }
    public String getBlockNumber(){
        return blockNumberTextField.getText();
    }
    public String getRoomNumber(){
        return roomNumberTextField.getText();
    }
    public String getDescription(){
        return descriptionTextPane.getText();
    }


    @Override
    public Integer updateDataBase() {
        String url = JavaConnection.URL;
        MaintenanceRequest request = new MaintenanceRequest(student.getsId());
        request.setDescription(getDescription());
        request.setBuildingNo(getBlockNumber());
        request.setRoomNO(getBlockNumber());
        JavaConnection javaConnection = new JavaConnection(url);
        Integer updateStatus = 0;
        String query = "INSERT INTO report(reportId,reporterId,reportType,description,roomNumber,buildingNumber)" +
                "VALUES(\'"+request.getRequesterId() + "\',\'" + request.getRequestType()+ "\',\'" +request.getDescription()+"\',\'"+
                request.getRoomNO()+"\',\'"+ request.getBuildingNo()+"\');";
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
    public void showParentComponent() {
        parentComponent.setVisible(true);
    }
}
