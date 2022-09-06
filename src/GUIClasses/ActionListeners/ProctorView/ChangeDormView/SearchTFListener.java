package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import GUIClasses.ProctorViews.ChangeDormView;
import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SearchTFListener implements FocusListener {
    private ChangeDormView parentComponent;
    public SearchTFListener(ChangeDormView parentComponent){this.parentComponent = parentComponent;}

    @Override
    public void focusGained(FocusEvent e) {
        parentComponent.setStudent(null);
    }

    @Override
    public void focusLost(FocusEvent e) {
        boolean studentFound = parentComponent.setStudentIfFound();
        if(!studentFound){
            JOptionPane.showMessageDialog(parentComponent,"Couldn't find the student with this id");
        }
    }
}
