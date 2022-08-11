package GUIClasses.ActionListeners;

import BasicClasses.Others.Cloth;
import GUIClasses.StudentViews.ClothTakeOutForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClothTakeOutAddButtonListener implements ActionListener {
    private ClothTakeOutForm parentComponent;
    public ClothTakeOutAddButtonListener(ClothTakeOutForm parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Cloth tmp = parentComponent.getClothInfo();
            if(tmp.getClothAmount()>0)
                if(tmp.getClothName().equals(""))
                    JOptionPane.showMessageDialog(parentComponent.getMainPanel(),"Name can't be empty",
                            "Invalid Input error",JOptionPane.ERROR_MESSAGE);
                else if(parentComponent.checkSimilarNameCloth(tmp))
                    JOptionPane.showMessageDialog(parentComponent.getMainPanel(),"You can't add same cloth twice.",
                            "Invalid Input error",JOptionPane.ERROR_MESSAGE);
                else{
                    parentComponent.getClothTable().addCloth(tmp);
                    parentComponent.addClothToView(tmp);
                    parentComponent.clear();
                }
            else
                JOptionPane.showMessageDialog(parentComponent.getMainPanel(),"Amount is invalid. cloth not added. " +
                                "Make sure to enter amount greater than zero","Invalid Input error",
                        JOptionPane.INFORMATION_MESSAGE);
        }
        catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(parentComponent.getMainPanel(),"Amount can't be empty",
                    "Invalid amount error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
