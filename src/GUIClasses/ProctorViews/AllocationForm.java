package GUIClasses.ProctorViews;

import BasicClasses.Persons.Proctor;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;

public class AllocationForm extends JFrame implements Views {
    private JPanel mainPanel;
    private JTextField buildingNoTF;
    private JButton allocateButton;
    private JPanel innerPanel;
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

    public void goToParent(){
        this.dispose();
        this.parentComponent.setVisible(true);
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Manual Allocation");
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(parentComponent);
        this.setVisible(true);
    }
}
