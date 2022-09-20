package GUIClasses.ProctorViews;

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
        for(int i = 0; i<=100; i++){
            try {
                Thread.sleep(1000);
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
        this.setLocationRelativeTo(parentComponent);
        this.setSize(400,300);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        loadingProgressBar.setStringPainted(true);

        this.setVisible(true);
    }
}
