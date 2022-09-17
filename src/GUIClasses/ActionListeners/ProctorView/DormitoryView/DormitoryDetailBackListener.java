package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.DormitoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DormitoryDetailBackListener implements ActionListener {
    private DormitoryView parentComponent;
    public DormitoryDetailBackListener(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
