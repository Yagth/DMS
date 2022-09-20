package GUIClasses.ProctorViews;

import BasicClasses.Persons.Proctor;
import BasicClasses.Persons.Student;
import GUIClasses.ActionListeners.ProctorView.AllocateDormView.SingleStudentAllocationListener;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SingleStudentAllocationForm extends JFrame implements Views {
    private JPanel innerPanel;
    private JTextField buildingNoTF;
    private JButton allocateButton;
    private JPanel mainPanel;
    private JTextField roomNoTF;
    private StudentDetailView parentComponent;
    private Proctor proctor;
    public SingleStudentAllocationForm(Proctor proctor, StudentDetailView parentComponent){
        this.proctor = proctor;
        this.parentComponent = parentComponent;
        setUpGUi();
    }

    public Proctor getProctor() {
        return proctor;
    }

    public StudentDetailView getParentComponent() {
        return parentComponent;
    }

    public Student getStudent(){
        return parentComponent.getStudent();
    }

    public String getBuildingNumber(){
        return buildingNoTF.getText();
    }

    public String getRoomNumber(){
        return roomNoTF.getText();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Manual Allocation");
        this.setSize(800,300);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(parentComponent);

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        allocateButton.addActionListener(new SingleStudentAllocationListener(this));
        this.setVisible(true);
    }
}
