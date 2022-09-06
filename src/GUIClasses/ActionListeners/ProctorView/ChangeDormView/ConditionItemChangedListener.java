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
        String buildingNo = parentComponent.getBuildingNo();
        String roomNo = parentComponent.getDormNo();
        int year = parentComponent.getYear();

        if(condition.equals("Change single student")){
            parentComponent.updateViewOnCondition(true);
            parentComponent.revalidate();
        }
        else {
            String query = "SELECT COUNT(SID) AS noOfStudents FROM STUDENT WHERE BuildingNumber='"+buildingNo+"' AND RoomNumber='"+roomNo+"' AND year="+year;
            parentComponent.updateViewOnCondition(false);
            parentComponent.setNumberOfStudentsL(parentComponent.getNoOfStudent(query));
            parentComponent.revalidate();
        }
    }
}
