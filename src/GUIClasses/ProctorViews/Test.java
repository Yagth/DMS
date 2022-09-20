package GUIClasses.ProctorViews;

import GUIClasses.ChangePasswordForm;
import GUIClasses.LoginPage;

import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (Exception ex){
            //DO nothing.
        }
        try{
            LoginPage loginPage = new LoginPage();
            loginPage.getUserNameTF().setFont(new Font("Californian FB",Font.PLAIN,14));
            loginPage.getUsernameTF().requestFocus();
            SwingUtilities.updateComponentTreeUI(loginPage);
        } catch (Exception ex){
            //DO nothing.
        }
    }
}
