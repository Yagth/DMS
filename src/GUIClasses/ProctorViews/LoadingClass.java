package GUIClasses.ProctorViews;

import BasicClasses.Others.LoadingThread;
import GUIClasses.Interfaces.Views;

import javax.swing.*;

public class LoadingClass extends JFrame implements Views {
    private JProgressBar loadingProgressBar;
    private JPanel mainPanel;
    private JLabel loadingL;
    private DormitoryView parentComponent;

    public LoadingClass(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
        setUpGUi();
    }

    public void incrementDots(){
        String labelText = loadingL.getText();

        if(labelText.substring(labelText.length()-3).equals("...")){
            labelText = labelText.substring(0,labelText.length()-3);
        } else{
            labelText += ".";
        }
        loadingL.setText(labelText);
    }
    public void fill(){
        System.out.println("Inside fill method.");
        for(int i = 0; i<=100; i++){
            try {
                Thread.sleep(1000);
                System.out.println("Filling: "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loadingProgressBar.setValue(i);
            incrementDots();
        }
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Loading");
        this.setContentPane(mainPanel);
        this.setSize(400,200);
        this.setLocationRelativeTo(parentComponent);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}
