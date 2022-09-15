package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import GUIClasses.ProctorViews.DormitoryView;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FilterConditionItemListener implements ItemListener {
    private DormitoryView parentComponent;
    public FilterConditionItemListener(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        parentComponent.setYearTAText("");
        String condition = parentComponent.getSelectedCondition();

        if(condition.equals("Year of Students")) parentComponent.setYearVisibility(true);
        else parentComponent.setYearVisibility(false);

        parentComponent.revalidate();
    }
}
