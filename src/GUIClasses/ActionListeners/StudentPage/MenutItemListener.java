package GUIClasses.ActionListeners.StudentPage;

import GUIClasses.StudentViews.StudentPage;

public abstract class MenutItemListener {
    protected StudentPage parentComponent;
    MenutItemListener(StudentPage parentComponent){
        this.parentComponent = parentComponent;
    }
    public void hideParentComponent(){
        parentComponent.setVisible(false);
    }
}
