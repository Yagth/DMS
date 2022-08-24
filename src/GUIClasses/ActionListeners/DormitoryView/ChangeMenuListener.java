package GUIClasses.ActionListeners.DormitoryView;

import GUIClasses.ProctorViews.DormitoryView;

import java.awt.event.ActionEvent;

public class ChangeMenuListener extends MenuListener{
    public ChangeMenuListener(DormitoryView parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hideParentComponent();
        new ChangeMenuListener(parentComponent);
    }
}
