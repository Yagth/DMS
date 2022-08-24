package GUIClasses.ProctorViews;

import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.ChangeBackButtonListener;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangeDormView extends JFrame implements Views {
    private JPanel mainPanel;
    private JComboBox conditions;
    private JFormattedTextField fromBuildingNoTF;
    private JFormattedTextField fromRoomNoTF;
    private JFormattedTextField searchTF;
    private JFormattedTextField toBuildingNoTF;
    private JFormattedTextField toRoomNoTF;
    private JLabel fromRoomNumberL;
    private JLabel toRoomNoL;
    private JLabel conditionsL;
    private JLabel searchStudentL;
    private JButton backButton;
    private JButton changeButton;
    private JLabel numberOfStudentsL;
    private JFormattedTextField yearTF;
    private Proctor proctor;
    private DormitoryView parentComponent;
    public ChangeDormView(Proctor proctor, DormitoryView parentComponent){
        this.proctor = proctor;
        this.parentComponent = parentComponent;
        setUpGUi();
    }
    public ChangeDormView(){
        this(null,null);
    }//For debugging only constructor.
    public void showParentComponent(){
        parentComponent.setVisible(true);
    }

    public String getSelectedCondition(){
        return (String) conditions.getSelectedItem();
    }

    public String getDormNo(){
        return fromRoomNoTF.getText();
    }
    public String getBuildingNo(){
        return fromBuildingNoTF.getText();
    }
    public String getDestinationBuildingNo(){
        return toBuildingNoTF.getText();
    }
    public String getDestinationRoomNo(){
        return toRoomNoTF.getText();
    }
    public int getNoOfStudent(){
        //This function will return the number of
        // students that satisfy the condition selected in the condition's comboBox.
        return 0;
    }
    public void updateViewOnCondition(){
        /*
        This method will set the invisible components to visible and
        vice versa by checking the selected condition in the comboBox.
         */
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Change Dormitory");
        this.setSize(500,300);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(parentComponent);
        backButton.addActionListener(new ChangeBackButtonListener(this));
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
        conditions.addItem("Change Batch of students");
        conditions.addItem("Change single student");
    }
}
