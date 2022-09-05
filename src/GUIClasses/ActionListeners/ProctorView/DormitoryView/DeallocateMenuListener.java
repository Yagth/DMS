package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import GUIClasses.ProctorViews.DeallocateDormView;
import GUIClasses.ProctorViews.DormitoryView;

import java.awt.event.ActionEvent;

public class DeallocateMenuListener extends MenuListener{
    public DeallocateMenuListener(DormitoryView parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hideParentComponent();
        new DeallocateDormView(parentComponent,parentComponent.getProctor());
    }
}
