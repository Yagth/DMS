package GUIClasses.ActionListeners.StudentView.StudentPage;

import GUIClasses.StudentViews.ClothTakeOutForm;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClothTakeOutMenuItemListener extends MenuItemListener implements ActionListener {
    public ClothTakeOutMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ClothTakeOutForm(parentComponent.getStudent(),parentComponent);
        hideParentComponent();
    }
}
