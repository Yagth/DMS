package GUIClasses.ProctorViews;

import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.ProctorView.AllocateDormView.ManualAllocationButtonListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AllocationForm extends JFrame implements Views {
    private JPanel mainPanel;
    private JTextField buildingNoTF;
    private JButton allocateButton;
    private JPanel innerPanel;
    private JProgressBar loadingProgressBar;
    private JLabel loadingL;
    private DormitoryView parentComponent;
    private Proctor proctor;

    public AllocationForm(Proctor proctor, DormitoryView parentComponent){
        this.proctor = proctor;
        this.parentComponent = parentComponent;
        setUpGUi();
    }

    public Proctor getProctor(){
        return proctor;
    }
    public String getBuildingNumber(){
        return buildingNoTF.getText();
    }
    public DormitoryView getParentComponent(){
        return parentComponent;
    }

    public void goToParent(){
        this.dispose();
        this.parentComponent.setVisible(true);
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

        allocateButton.addActionListener(new ManualAllocationButtonListener(this));
        this.setVisible(true);
    }
}
