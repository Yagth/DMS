package GUIClasses.ActionListeners.ProctorView.DeallocateDormView;

import GUIClasses.ProctorViews.DeallocateDormView;

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
            parentComponent.getNumberOfStudentsL().setText("");
            parentComponent.adjustVisisblity(true);
            parentComponent.revalidate();
        }
        else if(selectedCondition.equals("deallocate non eligible")){
            parentComponent.getNumberOfStudentsL().setText("");
            parentComponent.adjustVisisblity(false);
            parentComponent.revalidate();
            parentComponent.setNumberOfStudentsL();
        }
    }
}
