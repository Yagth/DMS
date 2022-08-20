package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.ExtendDormStayForm;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentExtendDormMenuItemListener extends StudentPageMenutItemListener implements ActionListener {
    public StudentExtendDormMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ExtendDormStayForm tmp = new ExtendDormStayForm();
        if(tmp.isDisplayable()){
            hideParentComponent();
        }
    }
}
