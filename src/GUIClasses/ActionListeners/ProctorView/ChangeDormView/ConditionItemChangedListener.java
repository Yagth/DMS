package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ConditionItemChangedListener implements ItemListener {
    private ChangeDormView parentComponent;
    public ConditionItemChangedListener(ChangeDormView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        String condition = parentComponent.getSelectedCondition();
        String query;
        if(condition.equals("Change Batch of students")){

        }
    }
}
