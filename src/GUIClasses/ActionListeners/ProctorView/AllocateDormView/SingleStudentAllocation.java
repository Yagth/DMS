package GUIClasses.ActionListeners.ProctorView.AllocateDormView;

import GUIClasses.ProctorViews.StudentDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleStudentAllocation implements ActionListener {
    private StudentDetailView parentComponent;
    private String buildingNumber;
    private String roomNumber;
    public SingleStudentAllocation(StudentDetailView parentComponent){
        this.parentComponent = parentComponent;
    }
    public void allocateStudent(){

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
