package GUIClasses.ActionListeners.StudentView.StudentPage;

import GUIClasses.StudentViews.RequestForDormitory;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestForDormMenuItemListener extends MenuItemListener implements ActionListener {
    public RequestForDormMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new RequestForDormitory(parentComponent.getStudent(),parentComponent);
        hideParentComponent();
    }
}
