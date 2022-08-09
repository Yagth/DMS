package GUIClasses.StudentViews;

import GUIClasses.Interfaces.Views;

import javax.swing.*;

public class MaintananceRequest extends JFrame implements Views {
    private JPanel mainPanel;
    private JPanel innerPanel;
    private JLabel locationLabel;
    private JTextField blockNumberTextField;
    private JTextField roomNumberTextField;
    private JButton submitButton;
    private JLabel discritptionLable;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JLabel buildingNoLabel;
    private JLabel roomNoLabel;
    private JTextPane discription;
    public final int WIDTH = 500;
    public final int HEIGHT = 230;


    public MaintananceRequest(){
       setUpGUi();
    }

    @Override
    public void setUpGUi() {
        this.setTitle("Maintenance Request");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}
