package GUIClasses.ActionListeners.ClothTakeOut;

import BasicClasses.Others.Cloth;
import GUIClasses.StudentViews.ClothTakeOutForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddButtonListener implements ActionListener {
    private ClothTakeOutForm parentComponent;
    public AddButtonListener(ClothTakeOutForm parentComponent){
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
                    parentComponent.getClothRequest().addCloth(tmp);
                    parentComponent.addDataToTable(tmp);
                    parentComponent.refreshTable();
                    parentComponent.clear();
                    parentComponent.getClothNameTF().requestFocus();
                }
            else
                JOptionPane.showMessageDialog(parentComponent.getMainPanel(),"Amount is invalid. cloth not added. " +
                                "Make sure to enter amount greater than zero","Invalid Input error",
                        JOptionPane.INFORMATION_MESSAGE);
        }
        catch (NumberFormatException ex){
            boolean amountIsEmpty = parentComponent.getClothAmountTF().getText().equals("");
            if(amountIsEmpty)
                JOptionPane.showMessageDialog(parentComponent.getMainPanel(),"Amount can't be empty",
                        "Empty amount error",JOptionPane.ERROR_MESSAGE);
            else
            JOptionPane.showMessageDialog(parentComponent.getMainPanel(),"Invalid input in the amount field",
                    "Invalid amount error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
