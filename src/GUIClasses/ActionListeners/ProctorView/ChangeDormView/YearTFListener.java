package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class YearTFListener implements FocusListener {
    private ChangeDormView parentComponent;
    public YearTFListener(ChangeDormView parentComponent){this.parentComponent = parentComponent;}

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        String query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT WHERE year="
                +parentComponent.getYear();
        int noOfStudents = parentComponent.getNoOfStudent(query);
        parentComponent.setNumberOfStudentsL(noOfStudents);
    }
}
