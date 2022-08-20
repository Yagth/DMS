package GUIClasses.StudentViews;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import GUIClasses.ActionListeners.ExtendDormSubmitButtonListener;
import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.Calendar;

public class ExtendDormStayForm extends JFrame implements RequestViews {
    private JPanel mainPanel;
    private JPanel innerPanel;
    private JTextPane descriptionPane;
    private JLabel titleLabel;
    private JButton submitButton;
    private JScrollPane descriptionSP;
    private Student student;
    private StudentPage parentComponent;
    private int roomNo = 49; //This part is only for debugging.
    private int buildingNo = 40; //This part is only for debugging.
    private int reporterId = 4565; //This part is only for debugging.
    private static final int WIDTH = 550;
    private static final int HEIGHT = 250;

    public ExtendDormStayForm(Student student, StudentPage parentComponent){
        this.student = student;
        this.parentComponent = parentComponent;
        setUpGUi();
    }

    public String getDescription(){
        return descriptionPane.getText();
    }
    @Override
    public Integer updateDataBase() {
        String url = JavaConnection.URL;
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        String requestType = this.getTitle();
        JavaConnection javaConnection = new JavaConnection(url);
        Integer updateStatus = 0;
        String query = "INSERT INTO request(reportedID,requestType,roomNO,blockNo,reportDate,description)" +
                "VALUES(\'"+reporterId+"\',\'"+requestType+"\',\'"+roomNo+"\',\'"+buildingNo+"\',\'"+date+"\',\'"+this.getDescription()+"\')";
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
