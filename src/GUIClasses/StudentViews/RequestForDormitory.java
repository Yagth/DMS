package GUIClasses.StudentViews;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.RequestForNewDorm;
import GUIClasses.ActionListeners.StudentView.RequestForDormitorySubmitButtonListener;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestForDormitory extends JFrame implements RequestViews {
    private JLabel titleLabel;
    private JTextField subcityTF;
    private JPanel inputPanel;
    private JLabel subcityLabel;
    private JTextField woredaTF;
    private JTextPane descriptionPane;
    private JScrollPane descriptionSP;
    private JLabel descriptionLabel;
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JButton submitButton;
    private Student student;
    private StudentPage parentComponent;
    private JavaConnection javaConnection;
    private RequestForNewDorm request;
    private int reporterId = 4556; //This part is in only for debugging purpose.
    private static final int WIDTH = 600;
    private static final int HEIGHT = 250;


    public RequestForDormitory(Student student,StudentPage parentComponent){
        javaConnection = new JavaConnection(JavaConnection.URL);
        request = new RequestForNewDorm(student.getPlaceOfOrigin(),student.getsId());
        this.student = student;
        this.parentComponent = parentComponent;
        setUpGUi();
    }
    public void setRequestAddress(){
        request.setRequesterAddress(student.getPlaceOfOrigin(),getSubcity(),getWoreda());
    }
    public String getSubcity(){
        return this.subcityTF.getText();
    }
    public String getWoreda(){
        return this.woredaTF.getText();
    }
    public String getDescription(){
        return JavaConnection.stripCotation(this.descriptionPane.getText());
    }

    @Override
    public Integer updateDataBase() {
        setRequestAddress();
        request.setDescription(getDescription());
        Integer updateStatus = 0;
        int tmp1 = 0,tmp2 = 0;
        String query = "INSERT INTO report(reporterId,reportType,description)" +
                "VALUES(\'"+request.getRequesterId() + "\',\'" + request.getRequestType()+ "\',\'" +request.getDescription()+"\');";
        if (javaConnection.isConnected()) tmp1 = javaConnection.insertQuery(query); //If query is successful the java connection returns 1.
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

    @Override
    public void setUpGUi() {
        this.setContentPane(mainPanel);
        this.setTitle("RequestForDormitory");
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        submitButton.addActionListener(new RequestForDormitorySubmitButtonListener(this));
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        ImageIcon tmp = new ImageIcon("Images/AAULOGOSmall.png");
        Image titleLogo = tmp.getImage();

        this.setIconImage(titleLogo);

        this.setVisible(true);
    }
}
