package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.ClothTakeOutForm;
import GUIClasses.StudentViews.StudentPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentClothTakeOutMenuItemListener extends StudentPageMenutItemListener implements ActionListener {
    public StudentClothTakeOutMenuItemListener(StudentPage parentComponent){
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClothTakeOutForm tmp =  new ClothTakeOutForm();
        if(tmp.isDisplayable()) hideParentComponent();
        else showParentComponent();
    }
}
