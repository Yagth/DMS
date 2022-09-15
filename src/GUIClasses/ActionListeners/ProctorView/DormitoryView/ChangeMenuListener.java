package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import GUIClasses.ProctorViews.ChangeDormView;
import GUIClasses.ProctorViews.DormitoryView;

import java.awt.event.ActionEvent;

public class ChangeMenuListener extends MenuListener{
    public ChangeMenuListener(DormitoryView parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hideParentComponent();
        new ChangeDormView(parentComponent.getProctor(),parentComponent);
    }
}
