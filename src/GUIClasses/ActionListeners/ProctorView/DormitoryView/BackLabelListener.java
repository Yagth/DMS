package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import GUIClasses.ProctorViews.DormitoryView;

import java.awt.*;
import java.awt.event.MouseEvent;

public class BackLabelListener extends LabelListener {
    public BackLabelListener(DormitoryView parentComponent){
        super(parentComponent);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        parentComponent.dispose();
        parentComponent.showParentComponent();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        parentComponent.getBackLabel().setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        parentComponent.getBackLabel().setBackground(null);
    }
}
