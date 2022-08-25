package GUIClasses.ProctorViews;

import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.DeallocateBackButtonListener;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    public void showParentComponent(){
        parentComponent.setVisible(true);
    }
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

        this.setVisible(true);
        conditions.addItem("deallocate Batch of students");
        conditions.addItem("deallocate non eligible");

    }
}
