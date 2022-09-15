package GUIClasses.ActionListeners.ProctorView.DeallocateDormView;

import GUIClasses.ProctorViews.DeallocateDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeallocateBackButtonListener implements ActionListener {
    private DeallocateDormView parentComponent;
    public DeallocateBackButtonListener(DeallocateDormView parentComponent){this.parentComponent = parentComponent;}
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
