package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import GUIClasses.ProctorViews.DormitoryDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DormitoryDetailBackListener implements ActionListener {
    private DormitoryDetailView parentComponent;
    public DormitoryDetailBackListener(DormitoryDetailView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
