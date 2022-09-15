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
        String roomNo = "";
        int year = parentComponent.getYear();

        if(condition.equals("Change single student")){
            try{
                roomNo = String.valueOf(parentComponent.getStudent().getDormNo());
            } catch (NullPointerException ex){
                ex.printStackTrace(); // For debugging purpose only.
            }
            parentComponent.updateViewOnCondition(true);
            parentComponent.pack();
            parentComponent.revalidate();
        }
        else {
            String query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT WHERE BuildingNumber='"+buildingNo+"' AND RoomNumber='"+roomNo+"' AND year="+year;
            parentComponent.updateViewOnCondition(false);
            parentComponent.setNumberOfStudentsL(parentComponent.getNoOfStudent(query));
            parentComponent.pack();
            parentComponent.revalidate();
        }
    }
}
