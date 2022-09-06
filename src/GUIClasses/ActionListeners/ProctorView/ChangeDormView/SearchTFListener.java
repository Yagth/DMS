package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import GUIClasses.ProctorViews.ChangeDormView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchTFListener implements ActionListener {
    private ChangeDormView parentComponent;
    public SearchTFListener(ChangeDormView parentComponent){this.parentComponent = parentComponent;}
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean studentFound = parentComponent.setStudentIfFound();
        if(!studentFound){
            JOptionPane.showMessageDialog(parentComponent,"Couldn't find the student with this id");
        }
    }
}
