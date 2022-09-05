package GUIClasses.ActionListeners.ProctorPage.Deallocate;

import GUIClasses.ProctorViews.DeallocateDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YearTFListener implements ActionListener {
    private DeallocateDormView parentComponent;
    public YearTFListener(DeallocateDormView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.setNumberOfStudentsL();
    }
}
