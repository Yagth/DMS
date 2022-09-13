package GUIClasses.ActionListeners.StudentView;

import GUIClasses.StudentViews.SeeYourRequests;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeeYourRequestBackButtonListener implements ActionListener {
   private SeeYourRequests parentComponent;
    public SeeYourRequestBackButtonListener(SeeYourRequests parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
