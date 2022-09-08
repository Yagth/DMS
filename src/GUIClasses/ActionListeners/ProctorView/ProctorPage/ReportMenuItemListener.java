package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.ReportsView;

import java.awt.event.ActionEvent;

public class ReportMenuItemListener extends MenuItemListener{
    public ReportMenuItemListener(ProctorPage parentComponent){
        super(parentComponent);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        hideParentComponent();
        new ReportsView(parentComponent,parentComponent.getProctor().getpId());
    }
}
