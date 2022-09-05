package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import GUIClasses.ProctorViews.DormitoryView;

import java.awt.event.MouseListener;

public abstract class LabelListener implements MouseListener {
    protected DormitoryView parentComponent;
    LabelListener(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
    }
    public void hideParentComponent(){
        parentComponent.setVisible(false);
    }
}
