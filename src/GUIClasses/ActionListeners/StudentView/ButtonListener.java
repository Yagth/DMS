package GUIClasses.ActionListeners.StudentView;

import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.StudentView;

import java.awt.event.ActionListener;

public abstract class ButtonListener implements ActionListener {
    protected StudentView parentComponent;
    ButtonListener(StudentView parentComponent){this.parentComponent = parentComponent;}
    public void hideParentComponent(){
        parentComponent.setVisible(false);
    }
}
