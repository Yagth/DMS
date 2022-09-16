package GUIClasses.ProctorViews;

import GUIClasses.LoginPage;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(new LoginPage());
    }
}
