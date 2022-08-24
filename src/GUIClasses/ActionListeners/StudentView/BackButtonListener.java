package GUIClasses.ActionListeners.StudentView;

import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.StudentView;

import java.awt.event.ActionEvent;

public class BackButtonListener extends ButtonListener{
    public BackButtonListener(StudentView parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
