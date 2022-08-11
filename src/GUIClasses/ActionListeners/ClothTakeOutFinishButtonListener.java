package GUIClasses.ActionListeners;

import GUIClasses.StudentViews.ClothTakeOutForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClothTakeOutFinishButtonListener implements ActionListener {
    ClothTakeOutForm parentComponent;
    public ClothTakeOutFinishButtonListener(ClothTakeOutForm parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (parentComponent.getClothTable().getClothsList().size() != 0) {
            Integer updateStatus = parentComponent.updateDataBase();
            parentComponent.displayUpdateStatus(updateStatus);
            parentComponent.dispose();
        }
        else{
            JOptionPane.showMessageDialog(parentComponent,
                    "No cloths added. Make sure to add First","Empty List",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
