package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FromBuildingNoTFListener implements FocusListener {
    private ChangeDormView parentComponent;
    public FromBuildingNoTFListener(ChangeDormView parentComponent){this.parentComponent = parentComponent;}
    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        String query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT WHERE year="
                +parentComponent.getYear()+ " AND BuildingNumber='"+parentComponent.getBuildingNo()+"'";
        System.out.println("Query: "+query);
        int noOfStudents = parentComponent.getNoOfStudent(query);
        System.out.println("Number of students: "+noOfStudents);
        parentComponent.setNumberOfStudentsL(noOfStudents);
    }
}
