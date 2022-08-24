package GUIClasses.ActionListeners.ProctorPage;

import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.StudentView;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;

public class SeeStudentMenuListener extends MenuItemListener{
    public SeeStudentMenuListener(ProctorPage parentComponent){
        super(parentComponent);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new StudentView(parentComponent,parentComponent.getProctor());
    }
}
