package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.StudentPage;

public abstract class StudentPageMenutItemListener {
    StudentPage parentComponent;
    StudentPageMenutItemListener(StudentPage parentComponent){
        this.parentComponent = parentComponent;
    }
}
