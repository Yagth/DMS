package GUIClasses.ActionListeners.StudentView;

import GUIClasses.ReportDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportDetailViewBackButtonListener implements ActionListener {
    private ReportDetailView parentComponent;
    public ReportDetailViewBackButtonListener(ReportDetailView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }
}
