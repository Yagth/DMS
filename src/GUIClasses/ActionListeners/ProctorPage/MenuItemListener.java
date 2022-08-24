package GUIClasses.ActionListeners.ProctorPage;

import GUIClasses.ProctorViews.ProctorPage;

import java.awt.event.ActionListener;

public abstract class MenuItemListener implements ActionListener {
    protected ProctorPage parentComponent;
    MenuItemListener(ProctorPage parentComponent){
        this.parentComponent = parentComponent;
    }
    public void hideParentComponent(){
        parentComponent.setVisible(false);
    }
}
