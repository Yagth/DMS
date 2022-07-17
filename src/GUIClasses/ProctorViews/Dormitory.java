package GUIClasses.ProctorViews;

import javax.swing.*;

public class Dormitory extends JFrame {
    private JFormattedTextField searchDormTA;
    private JButton searchButton;
    private JButton filterButton;
    private JPanel headingPanel;
    private JPanel mainPanel;
    private JLabel totalNoLabel;
    private JLabel totalSpaceNo;
    private JLabel emptyDormNoLabel;
    private JPanel totalDormInfoPanel;
    private JButton allocateButton;
    private JButton changeButton;
    private JButton deallocateButton;
    private JButton backButton;
    private JButton nextButton;
    private JButton previousButton;
    private JLabel dormLocation;
    private JLabel noStudentsLabel;
    private JPanel listOfDormPanel;
    private JLabel numberOfDorm;
    private JPanel buttonPanel;
    private JLabel searchDormLabel;
    private JLabel filterLabel;
    private JPanel dormLocationPanel;
    private JPanel numberOfStudentsPanel;
    private JPanel dormListPanel;
    private JComboBox filterList;

    public Dormitory(){
        this.setTitle("Dormitory");
        this.setContentPane(mainPanel);
        this.setSize(550,210);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        filterList.addItem("Year of Students");
        filterList.addItem("Available space");

    }


}
