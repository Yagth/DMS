package GUIClasses.ActionListeners.ClothTakeOut;

import GUIClasses.StudentViews.ClothTakeOutForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinishButtonListener implements ActionListener {
    ClothTakeOutForm parentComponent;
    public FinishButtonListener(ClothTakeOutForm parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (parentComponent.getClothRequest().getClothsList().size() != 0) {
            Integer updateStatus = parentComponent.updateDataBase();
            parentComponent.displayUpdateStatus(updateStatus);
            parentComponent.dispose();
            parentComponent.showParentComponent();
        }
        else{
            JOptionPane.showMessageDialog(parentComponent,
                    "No cloths added. Make sure to add First","Empty List",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
