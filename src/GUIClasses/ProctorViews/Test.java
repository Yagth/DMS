package GUIClasses.ProctorViews;

import GUIClasses.LoginPage;

import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        LoginPage loginPage = new LoginPage();
        loginPage.getUserNameTF().setFont(new Font("Californian FB",Font.PLAIN,14));
        loginPage.getUsernameTF().requestFocus();
        SwingUtilities.updateComponentTreeUI(loginPage);
    }
}
