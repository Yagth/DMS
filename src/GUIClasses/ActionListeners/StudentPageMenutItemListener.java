package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.StudentPage;

public abstract class StudentPageMenutItemListener {
    protected StudentPage parentComponent;
    StudentPageMenutItemListener(StudentPage parentComponent){
        this.parentComponent = parentComponent;
    }
    public void hideParentComponent(){
        parentComponent.setVisible(false);
    }
}
