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
    private JButton backButton;
    private JButton nextPageButton;
    private JButton previousPageButton;
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
    private JTextField yearTA;
    private JLabel RNOLabel;
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
        this.setVisible(true);
        filterList.addItem("Year of Students");
        filterList.addItem("Available space");

        ImageIcon searchButtonIcon = new ImageIcon("Icons/SearchIcon.png");
        searchButton.setIcon(searchButtonIcon);

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

    }
}
