package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import GUIClasses.Interfaces.Views;

import javax.swing.*;

public class DormitoryView extends JFrame implements Views {
    private JFormattedTextField searchBuildingNoTA;
    private JButton searchButton;
    private JButton filterButton;
    private JPanel headingPanel;
    private JPanel mainPanel;
    private JLabel totalNoLabel;
    private JLabel totalSpaceNo;
    private JLabel emptyDormNoLabel;
    private JPanel totalDormInfoPanel;
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
    private JLabel BNOLabel;
    private JFormattedTextField seatchRoomNoTA;
    private JLabel RNOLabel;
    private JLabel backLabel;
    private JLabel previousPageLabel;
    private JLabel nextPageLabel;
    private JTextField yearTA;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public DormitoryView(){
        setUpGUi();
    }


    @Override
    public void setUpGUi() {
        this.setTitle("DormitoryView");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        filterList.addItem("Year of Students");
        filterList.addItem("Available space");

        ImageIcon searchButtonIcon = new ImageIcon("Icons/SearchIcon.png");
        searchButton.setIcon(searchButtonIcon);
        ImageIcon backButtonIcon = new ImageIcon("Icons/backIcon.png");
        backLabel.setIcon(backButtonIcon);
        ImageIcon nextButtonIcon = new ImageIcon("Icons/nextIcon(3).png");
        nextPageLabel.setIcon(nextButtonIcon);
        ImageIcon previousButtonIcon = new ImageIcon("Icons/previousIcon-20x20.png");
        previousPageLabel.setIcon(previousButtonIcon);
        ImageIcon filterButtonIcon = new ImageIcon("Icons/FilterIcon.png");
        filterButton.setIcon(filterButtonIcon);

        JMenuBar menuBar = new JMenuBar();

        JMenu actions = new JMenu("Actions");

        JMenuItem allocate = new JMenuItem("Allocate Dorms");
        JMenuItem deallocate = new JMenuItem("Deallocate all dorms");
        JMenuItem change = new JMenuItem("Change");

        actions.add(allocate);
        actions.add(deallocate);
        actions.add(change);

        menuBar.add(actions);

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }
}
