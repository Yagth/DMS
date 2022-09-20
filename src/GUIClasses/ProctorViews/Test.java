package GUIClasses.ProctorViews;

import BasicClasses.Others.LoadingThread;
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
//            Thread loadingThread = new LoadingThread(new LoadingClass(null));
//            loadingThread.start();
//            for(int i = 0;i<100; i++){
//                System.out.println("This is in the main thread");
//                Thread.sleep(1000);
//            }
//            loadingThread.interrupt();

            LoginPage loginPage = new LoginPage();
            loginPage.getUserNameTF().setFont(new Font("Californian FB",Font.PLAIN,14));
            loginPage.getUsernameTF().requestFocus();
            SwingUtilities.updateComponentTreeUI(loginPage);
        } catch (Exception ex){
            //DO nothing.
        }
    }
}
