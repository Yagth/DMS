package GUIClasses.ActionListeners.StudentView.StudentPage;

import GUIClasses.StudentViews.SeeYourRequests;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeeYourRequestListener extends MenuItemListener implements ActionListener {
    public SeeYourRequestListener(StudentPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SeeYourRequests(parentComponent.getStudent(),parentComponent);
        hideParentComponent();
    }
}
