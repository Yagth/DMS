package GUIClasses.ActionListeners.ProctorView.StudentView;

import GUIClasses.ProctorViews.StudentView;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SearchTFFocusListener implements FocusListener {
    private StudentView parentComponent;
    public SearchTFFocusListener(StudentView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        if(parentComponent.getSearchText().equals(""))
            parentComponent.reloadTable();
    }
}
