package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YearTFListener implements ActionListener {
    private ChangeDormView parentComponent;
    public YearTFListener(ChangeDormView parentComponent){this.parentComponent = parentComponent;}
    @Override
    public void actionPerformed(ActionEvent e) {
        String query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENTS WHERE year="+parentComponent.getYear();
        int noOfStudents = parentComponent.getNoOfStudent(query);
        parentComponent.setNumberOfStudentsL(noOfStudents);
    }
}
