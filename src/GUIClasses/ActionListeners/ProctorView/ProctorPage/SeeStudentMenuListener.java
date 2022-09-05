package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.StudentView;

import java.awt.event.ActionEvent;

public class SeeStudentMenuListener extends MenuItemListener{
    public SeeStudentMenuListener(ProctorPage parentComponent){
        super(parentComponent);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        hideParentComponent();
        new StudentView(parentComponent,parentComponent.getProctor());
    }
}
