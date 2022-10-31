package GUIClasses.ProctorViews;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.ProctorView.ChangeDormView.FromBuildingNoTFListener;
import GUIClasses.ActionListeners.ProctorView.DeallocateDormView.*;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeallocateDormView extends JFrame implements Views {
    private JFormattedTextField fromBuildingNoTF;
    private JComboBox conditions;
    private JPanel mainPanel;
    private JButton backButton;
    private JButton deallocateButton;
    private JLabel numberOfStudentsL;
    private JFormattedTextField yearTF;
    private JLabel fromBuildingNoL;
    private Proctor proctor;
    private DormitoryView parentComponent;
    public DeallocateDormView(DormitoryView parentComponent, Proctor proctor){
        this.parentComponent = parentComponent;
        this.proctor = proctor;
        setUpGUi();
    }

    public int getYear(){
        try{
            return Integer.parseInt(yearTF.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please make sure there is a valid year input.");
            return 0;
        }
    }

    public JFormattedTextField getYearTF(){
        return yearTF;
    }
    public void showParentComponent(){
        parentComponent.setVisible(true);
    }
    public String getSelectedCondition(){
        return (String) conditions.getSelectedItem();
    }
    public String getBuildingNo(){
        return fromBuildingNoTF.getText();
    }
    public int getNoOfStudent(String query){
        /*This function will return the number of
         students that satisfy the condition selected
          in the condition's comboBox.*/

        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet = null;
        int numberOfStudents = 0;
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
        }
        try{
            while(resultSet.next()){
                numberOfStudents = resultSet.getInt("numberOfStudents"); // There will only be one row.
            }
        } catch (SQLException ex){
            ex.printStackTrace(); // For debugging.
        }
        return numberOfStudents;
    }
    @Override
    public void setUpGUi() {
        this.setContentPane(mainPanel);
        this.setTitle("Deallocate Dorm");
        this.setSize(600,300);
        this.setLocationRelativeTo(parentComponent);
        yearTF.setText("");
        yearTF.addFocusListener(new YearTFFocusListener(this));
        fromBuildingNoTF.addFocusListener(new FromDormTFFocusListener(this));
        deallocateButton.addActionListener(new DeallocateButtonListener(this));
        backButton.addActionListener(new DeallocateBackButtonListener(this));
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        ImageIcon logo = new ImageIcon("Images/DMS-logo.png");

        Image titleLogo = logo.getImage();

        this.setIconImage(titleLogo);

        this.setVisible(true);
        conditions.addItem("deallocate Batch of students");
        conditions.addItem("deallocate non eligible");
        conditions.setSelectedIndex(1);
        conditions.addItemListener(new DeallocateBatchItemListener(this));
        setNumberOfStudentsL();
        adjustVisisblity(false);
    }

    public JLabel getNumberOfStudentsL(){
        return numberOfStudentsL;
    }
    public void adjustVisisblity(boolean visibility){
        yearTF.setVisible(visibility);
        fromBuildingNoTF.setVisible(visibility);
        fromBuildingNoL.setVisible(visibility);
    }
    public void setNumberOfStudentsL(String buildingNumber){
        String condition = getSelectedCondition();
        if(condition.equals("deallocate Batch of students")){
            String query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT WHERE Year="+getYear()+" AND RoomNumber IS NOT NULL AND BuildingNumber='"+buildingNumber+"' ";
            numberOfStudentsL.setText(String.valueOf(getNoOfStudent(query)));
            numberOfStudentsL.setVisible(true);
        }
    }
    public void setNumberOfStudentsL(){
        String condition = getSelectedCondition();
        if(condition.equals("deallocate Batch of students")){
            String query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT WHERE Year="+getYear()+" AND RoomNumber IS NOT NULL AND BuildingNumber IS NOT NULL";
            numberOfStudentsL.setText(String.valueOf(getNoOfStudent(query)));
            numberOfStudentsL.setVisible(true);
        }
        else if(condition.equals("deallocate non eligible")){
            String query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT WHERE isEligible="+0+" AND RoomNumber IS NOT NULL AND BuildingNumber IS NOT NULL";
            numberOfStudentsL.setText(String.valueOf(getNoOfStudent(query)));
            numberOfStudentsL.setVisible(true);
        }
    }
    public String getFromBuildingNumber(){
        return fromBuildingNoTF.getText();
    }
    public Proctor getProctor(){
        return proctor;
    }
    public void makeParentComponentVisible(){
        parentComponent.setVisible(true);
    }
}
