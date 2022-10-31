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
        ImageIcon logo = new ImageIcon("Images/DMS-logo-small.png");

        JFrame displayFrame = new JFrame();

        Image titleLogo = logo.getImage();

        displayFrame.setIconImage(titleLogo);

        displayFrame.setUndecorated(true);
        displayFrame.setSize(new Dimension(SizeOfMajorClasses.WIDTH.getSize(), SizeOfMajorClasses.HEIGHT.getSize()));

        DisplayPanel imagePanel = new DisplayPanel(displayFrame.getWidth());
        displayFrame.add(imagePanel);

        displayFrame.setResizable(false);

        displayFrame.setLocationRelativeTo(null);
        displayFrame.setVisible(true);
        try {
            Thread.sleep(4500);
        } catch (Exception ex){

        }
        displayFrame.dispose();
    }
}
