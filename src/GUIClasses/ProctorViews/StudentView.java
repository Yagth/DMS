package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.StudentView.BackButtonListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudentView extends JFrame implements Views, TableViews {
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JFormattedTextField searchTF;
    private JButton searchButton;
    private JComboBox filterCondition;
    private JButton filterButton;
    private JPanel studentListPanel;
    private JTable studentListTable;
    private JPanel buttonPanel;
    private JButton nextButton;
    private JButton prevButton;
    private JButton backButton;
    private ProctorPage parentComponent;
    private Proctor proctor;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public StudentView(ProctorPage parentComponent,Proctor proctor){
        this.parentComponent = parentComponent;
        this.proctor = proctor;
        setUpGUi();
    }
    public void showParentComponent(){
        parentComponent.setVisible(true);
    }

    @Override
    public void setUpTable() {

    }

    @Override
    public void addDataToTable(Object object) {

    }

    @Override
    public void refreshTable() {

    }

    @Override
    public void setUpGUi() {
        setTitle("Students View");
        setContentPane(mainPanel);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        backButton.addActionListener(new BackButtonListener(this));
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        ImageIcon searchButtonIcon = new ImageIcon("Icons/SearchIcon.png");
        searchButton.setIcon(searchButtonIcon);
        ImageIcon filterButtonIcon = new ImageIcon("Icons/FilterIcon.png");
        filterButton.setIcon(filterButtonIcon);

        filterCondition.addItem("Year of students");
        filterCondition.addItem("Block");
        filterCondition.addItem("Eligibility");

        this.setVisible(true);
    }
}
