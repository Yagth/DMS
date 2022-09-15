package GUIClasses.ActionListeners.ProctorView;

import GUIClasses.ProctorViews.ReportsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllReportBackButtonListener implements ActionListener {
    private ReportsView parentComponent;
    public AllReportBackButtonListener(ReportsView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.refreshParentTable();
        parentComponent.showParentComponent();
    }
}
