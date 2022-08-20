package GUIClasses.ActionListeners.StudentPage;

import GUIClasses.StudentViews.RequestForDormitory;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestForDormMenuItemListener extends MenutItemListener implements ActionListener {
    public RequestForDormMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new RequestForDormitory(parentComponent.getUser(),parentComponent);
        hideParentComponent();
    }
}
