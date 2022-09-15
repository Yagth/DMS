package GUIClasses.StudentViews;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.ExtendDormStayRequest;
import GUIClasses.ActionListeners.StudentView.ExtendDormSubmitButtonListener;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExtendDormStayForm extends JFrame implements RequestViews {
    private JPanel mainPanel;
    private JPanel innerPanel;
    private JTextPane descriptionPane;
    private JLabel titleLabel;
    private JButton submitButton;
    private JScrollPane descriptionSP;
    private Student student;
    private StudentPage parentComponent;
    private JavaConnection javaConnection;
    private ExtendDormStayRequest request;
    private static final int WIDTH = 550;
    private static final int HEIGHT = 250;

    public ExtendDormStayForm(Student student, StudentPage parentComponent){
        javaConnection = new JavaConnection(JavaConnection.URL);
        request = new ExtendDormStayRequest(student.getsId());
        request.setRoomNumber(String.valueOf(student.getDormNo()));
        request.setBuildingNumber(String.valueOf(student.getBuildingNo()));
        this.student = student;
        this.parentComponent = parentComponent;
        setUpGUi();
    }

    public String getDescription(){
        return JavaConnection.stripCotation(descriptionPane.getText());
    }
    @Override
    public Integer updateDataBase() {
        request.setDescription(getDescription());
        Integer updateStatus = 0;
        int tmp1 = 0,tmp2 = 0;
        String query = "INSERT INTO report(reporterId,reportType,description,roomNumber,buildingNumber)" +
                "VALUES(\'"+request.getRequesterId() + "\',\'" + request.getRequestType()+ "\',\'" +request.getDescription() +"\', '"+request.getRoomNumber()+"', '"+request.getBuildingNumber()+"' );";
        if (javaConnection.isConnected()) tmp1 = javaConnection.insertQuery(query);//If query is successful the java connection returns 1.
        query = "INSERT INTO StudentMakesReport(SID,reportId,reportedDate)" +
                "VALUES(\'"+request.getRequesterId() + "\',\'" + getCurrentClothRequestId()+ "\',\'"+request.getRequestedDate()+"\');";
        if (javaConnection.isConnected()) tmp2 = javaConnection.insertQuery(query);//If query is successful the java connection returns 1.

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
        this.setTitle("Extend Dorm Stay Request");
        this.setContentPane(mainPanel);
        this.setSize(new Dimension(WIDTH,HEIGHT));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        submitButton.addActionListener(new ExtendDormSubmitButtonListener(this));
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
}
