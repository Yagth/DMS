package GUIClasses.ActionListeners.StudentView.StudentPage;

import GUIClasses.StudentViews.StudentPage;

public abstract class MenuItemListener {
    protected StudentPage parentComponent;
    MenuItemListener(StudentPage parentComponent){
        this.parentComponent = parentComponent;
    }
    public void hideParentComponent(){
        parentComponent.setVisible(false);
    }
}
