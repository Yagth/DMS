package GUIClasses.ActionListeners.ProctorPage.Deallocate;

import GUIClasses.ProctorViews.DeallocateDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DeallocateBatchItemListener implements ItemListener {
    private DeallocateDormView parentComponent;
    public DeallocateBatchItemListener(DeallocateDormView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        String selectedCondition = parentComponent.getSelectedCondition();
        if(selectedCondition.equals("deallocate Batch of students")){
            parentComponent.setYearTFVisibility(true);
            while(parentComponent.getYearTF().getText() == null){

            }
            parentComponent.setNumberOfStudentsL();
        }
        else if(selectedCondition.equals("deallocate non eligible")){
            parentComponent.setYearTFVisibility(false);
            parentComponent.setNumberOfStudentsL();
        }
    }
}
