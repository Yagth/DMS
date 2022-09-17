package GUIClasses.StudentViews;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.MaintenanceRequest;
import GUIClasses.ActionListeners.StudentView.MaintenanceSubmitButtonListener;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintenanceRequestForm extends JFrame implements RequestViews {
    private JPanel MainPanel;
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
    private  JavaConnection javaConnection;
    private Student student;
    private StudentPage parentComponent;
    private MaintenanceRequest request;
    String reporterId = "yep it is"; // This part here is only for debugging. It will be removed when the project is complete.
    public final int WIDTH = 500;
    public final int HEIGHT = 230;


    public MaintenanceRequestForm(Student student, StudentPage parentComponent){
        this.student = student;
        this.parentComponent = parentComponent;
        javaConnection = new JavaConnection(JavaConnection.URL);
        request = new MaintenanceRequest(student.getsId());
        setUpGUi();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Maintenance Request");
        this.setContentPane(MainPanel);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
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

        ImageIcon logo = new ImageIcon("Images/AAULOGO.png");

        Image titleLogo = logo.getImage();

        this.setIconImage(titleLogo);

        this.setVisible(true);
    }
    public String getBlockNumber(){
        return blockNumberTextField.getText();
    }
    public String getRoomNumber(){
        return roomNumberTextField.getText();
    }
    public String getDescription(){
        return JavaConnection.stripCotation(descriptionTextPane.getText());
    }

    @Override
    public Integer updateDataBase() {
        request.setDescription(getDescription());
        request.setBuildingNo(getBlockNumber());
        request.setRoomNO(getRoomNumber());
        Integer updateStatus = 0;
        int tmp1 = 0,tmp2 = 0;
        String query = "INSERT INTO report(reporterId,reportType,description,roomNumber,buildingNumber)" +
                "VALUES(\'"+request.getRequesterId() + "\',\'" + request.getRequestType()+ "\',\'" +request.getDescription()+"\',\'"+
                request.getRoomNO()+"\',\'"+ request.getBuildingNo()+"\');";
        if (javaConnection.isConnected()) tmp1 = javaConnection.insertQuery(query);//If query is successful the java connection returns 1.
        query = "INSERT INTO StudentMakesReport(SID,reportId,reportedDate)" +
                "VALUES(\'"+request.getRequesterId() + "\',\'" + getCurrentClothRequestId()+ "\',\'" +request.getRequestedDate()+"\');";
        if(javaConnection.isConnected()) tmp2 = javaConnection.insertQuery(query); //If query is successful the java connection returns 1.
        if(tmp1==1 & tmp2==1) updateStatus = 1; //If both queries are successful.
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

    @Override
    public Integer getCurrentClothRequestId() {
        String query = "SELECT LAST_VALUE(ReportId) OVER(ORDER BY reportType) reportId FROM Report where reportType=\'"+request.getRequestType()+"\';";
        ResultSet tmp = javaConnection.selectQuery(query);
        int requestId = 0;
        try{
            if(tmp.next())
                requestId = tmp.getInt("ReportId");
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return requestId;
    }
}
