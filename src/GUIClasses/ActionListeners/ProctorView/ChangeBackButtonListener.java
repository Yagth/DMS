package GUIClasses.ActionListeners.ProctorView;

import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeBackButtonListener implements ActionListener {
    private ChangeDormView parentComponent;
    public ChangeBackButtonListener(ChangeDormView parentComponent){this.parentComponent = parentComponent;}
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
