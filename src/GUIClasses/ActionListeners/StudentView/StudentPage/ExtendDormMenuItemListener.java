package GUIClasses.ActionListeners.StudentView.StudentPage;

import GUIClasses.StudentViews.ExtendDormStayForm;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendDormMenuItemListener extends MenuItemListener implements ActionListener {
    public ExtendDormMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new ExtendDormStayForm(parentComponent.getStudent(),parentComponent);
        hideParentComponent();
    }
}
