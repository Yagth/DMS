package GUIClasses.ActionListeners.ProctorView.DeallocateDormView;

import GUIClasses.ProctorViews.DeallocateDormView;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FromDormTFFocusListener implements FocusListener {
    private DeallocateDormView parentComponent;
    public FromDormTFFocusListener(DeallocateDormView parentComponent){
        this.parentComponent = parentComponent;
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        String buildingNumber = parentComponent.getFromBuildingNumber();
        parentComponent.setNumberOfStudentsL(buildingNumber);
    }
}
