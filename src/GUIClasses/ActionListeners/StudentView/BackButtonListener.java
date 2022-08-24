package GUIClasses.ActionListeners.StudentView;

import GUIClasses.ProctorViews.ProctorPage;

import java.awt.event.ActionEvent;

public class BackButtonListener extends ButtonListener{
    public BackButtonListener(ProctorPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
