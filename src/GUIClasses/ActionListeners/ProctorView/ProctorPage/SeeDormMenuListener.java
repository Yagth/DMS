package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import GUIClasses.ProctorViews.DormitoryView;
import GUIClasses.ProctorViews.ProctorPage;

import java.awt.event.ActionEvent;

public class SeeDormMenuListener extends MenuItemListener {
    public SeeDormMenuListener(ProctorPage parentComponent){
        super(parentComponent);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Proctor from seeDormMenuListener: "+parentComponent.getProctor().getFullName());//For debugging purpose.
        System.out.println("Proctor Id: "+parentComponent.getProctor().getpId());//For debugging only.
        new DormitoryView(parentComponent.getProctor(),parentComponent);
        hideParentComponent();
    }
}
