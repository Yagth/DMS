package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.StudentPage;

public abstract class StudentPageMenutItemListener {
    StudentPage parentComponent;
    StudentPageMenutItemListener(StudentPage parentComponent){
        this.parentComponent = parentComponent;
    }
    public void hideParentComponent(){
        parentComponent.setVisible(false);
    }
    public void showParentComponent(){
        parentComponent.setVisible(true);
    }
}
