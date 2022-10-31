package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import GUIClasses.DisplayPanel;
import GUIClasses.LoginPage;

import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String[] args) {

        displayFrame();//Displays an image for introduction.

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

    public static void displayFrame(){
        JFrame displayFrame = new JFrame();

        displayFrame.setUndecorated(true);
        displayFrame.setSize(new Dimension(SizeOfMajorClasses.WIDTH.getSize(), SizeOfMajorClasses.HEIGHT.getSize()));

        DisplayPanel imagePanel = new DisplayPanel(displayFrame.getWidth(), displayFrame.getHeight());
        displayFrame.add(imagePanel);

        displayFrame.setLocationRelativeTo(null);
        displayFrame.setVisible(true);
        try {
            Thread.sleep(3500);
        } catch (Exception ex){

        }
        displayFrame.dispose();
    }
}
