package GUIClasses.ActionListeners.ProctorView.StudentView;

import GUIClasses.ProctorViews.StudentView;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FilterConditionItemListener implements ItemListener {
    private StudentView parentComponent;
    public FilterConditionItemListener(StudentView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        parentComponent.clearInputTexts();

        String selectedCondition = parentComponent.getSelectedCondition();
        try{
            parentComponent.reloadTable();
            if(selectedCondition.equals("")){
                clearFilter();
                changeGui(false);
            } else if (selectedCondition.equals("Year of students")){
                changeGui("Year",true);
            }
            else if (selectedCondition.equals("Block")) {
                changeGui(true);
            }
            else {
                changeGui(false);
            }
        } catch (Exception ex){
            System.out.println("Inside the filter item listener");//For debugging only.
            ex.printStackTrace();//For debugging only.
        }
    }

    public void clearFilter(){
        parentComponent.getTableData().clear();
        parentComponent.reloadTable();
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
