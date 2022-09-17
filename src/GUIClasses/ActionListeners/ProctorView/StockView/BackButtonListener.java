package GUIClasses.ActionListeners.ProctorView.StockView;

import GUIClasses.ProctorViews.StockView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButtonListener implements ActionListener {
    private StockView parentComponent;
    public BackButtonListener(StockView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.goBackToParent();
    }
}
