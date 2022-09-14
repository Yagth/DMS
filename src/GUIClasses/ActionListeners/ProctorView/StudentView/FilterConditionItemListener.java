package GUIClasses.ActionListeners.ProctorView.StudentView;

import GUIClasses.ProctorViews.StudentView;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class FilterConditionItemListener implements ItemListener {
    private StudentView parentComponent;
    public FilterConditionItemListener(StudentView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        String selectedCondition = parentComponent.getSelectedCondition();
        if(selectedCondition.equals("")){
            clearFilter();
            changeGui(false);
        } else if (selectedCondition.equals("Year of students")) changeGui("Year",true);
          else if (selectedCondition.equals("Block")) changeGui(true);
          else changeGui(false);
    }

    public void clearFilter(){
        parentComponent.getTableData().clear();
        Vector<Vector<Object>> students = parentComponent.loadStudents();
        parentComponent.addDataToTable(students);
        parentComponent.refreshTable();
    }

    public void changeGui(String text, boolean visibility){
        parentComponent.changeTextForFilterInput(text);
        parentComponent.setBuildingInputVisibility(visibility);
        parentComponent.revalidate();
    }

    public void changeGui(boolean visibility){
        parentComponent.changeTextForFilterInput("Building Number");
        parentComponent.setBuildingInputVisibility(visibility);
        parentComponent.revalidate();
    }
}
