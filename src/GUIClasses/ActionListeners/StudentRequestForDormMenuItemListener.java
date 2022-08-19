package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.RequestForDormitory;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentRequestForDormMenuItemListener extends StudentPageMenutItemListener implements ActionListener {
    public StudentRequestForDormMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new RequestForDormitory();
    }
}
