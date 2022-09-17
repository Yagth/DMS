package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import BasicClasses.Persons.Proctor;
import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.StockView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StockMenuItemListener extends MenuItemListener{

    public StockMenuItemListener(ProctorPage parentComponent) {
        super(parentComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Proctor proctor = parentComponent.getProctor();
            parentComponent.setVisible(false);
            new StockView(parentComponent,proctor);
        } catch (NullPointerException ex){
            ex.printStackTrace();//For debugging only.
            JOptionPane.showMessageDialog(parentComponent,"Couldn't Open this page due to some error.",
                    "Error opening page",JOptionPane.ERROR_MESSAGE);
            parentComponent.setVisible(true);
        }
    }
}
