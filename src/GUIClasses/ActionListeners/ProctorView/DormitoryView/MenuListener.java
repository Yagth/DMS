package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import GUIClasses.ProctorViews.DormitoryView;

import java.awt.event.ActionListener;

public abstract class MenuListener implements ActionListener {
    protected DormitoryView parentComponent;
    MenuListener(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
    }
    public void hideParentComponent(){
        parentComponent.setVisible(false);
    }
}
