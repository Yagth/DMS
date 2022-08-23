package GUIClasses.ProctorViews;

import BasicClasses.Persons.Proctor;
import GUIClasses.Interfaces.Views;

import javax.swing.*;

public class DeallocateDormView extends JFrame implements Views {
    private JFormattedTextField fromBuildingNoTF;
    private JComboBox conditions;
    private JPanel mainPanel;
    private JButton backButton;
    private JButton deallocateButton;
    private JLabel numberOfStudentsL;
    private Proctor proctor;
    private DormitoryView parentComponent;
    public DeallocateDormView(DormitoryView parentComponent, Proctor proctor){
        this.parentComponent = parentComponent;
        this.proctor = proctor;
        setUpGUi();
    }

    public DeallocateDormView(){
        this(null,null);
    }//For debugging only constructor.

    public String getSelectedCondition(){
        return (String) conditions.getSelectedItem();
    }
    public String getBuildingNo(){
        return fromBuildingNoTF.getText();
    }
    public int getNoOfStudent(){
        //This function will return the number of
        // students that satisfy the condition selected in the condition's comboBox.
        return 0;
    }
    @Override
    public void setUpGUi() {
        this.setContentPane(mainPanel);
        this.setTitle("Deallocate Dorm");
        this.setSize(500,300);
        this.setLocationRelativeTo(parentComponent);
        this.setVisible(true);
        conditions.addItem("Change Batch of students");
        conditions.addItem("Change single student");

    }
}
