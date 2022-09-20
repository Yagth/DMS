package GUIClasses.ActionListeners.ProctorView.StudentView;

import GUIClasses.ProctorViews.SingleStudentAllocationForm;
import GUIClasses.ProctorViews.StudentDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllocateButtonListener implements ActionListener {
    private StudentDetailView parentComponent;

    public AllocateButtonListener(StudentDetailView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new SingleStudentAllocationForm(parentComponent.getProctor(),parentComponent);
    }
}

