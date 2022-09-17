package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.ProctorView.StockView.BackButtonListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class StockView extends JFrame implements Views, TableViews {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel stockDetailPanel;
    private JLabel buildingNumberL;
    private JPanel historyPanel;
    private JTable stockHistoryTable;
    private JPanel buttonPanel;
    private ProctorPage parentComponent;
    private Proctor proctor;
    private boolean readStatus;
    private JButton backButton;
    private Vector<Vector<Object>> tableData;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public StockView(ProctorPage parentComponent,Proctor proctor){
        this.proctor = proctor;
        this.parentComponent = parentComponent;
        setUpGUi();
        System.out.println("After setUpGui");//For debugging only.
    }

    public Vector<Vector<Object>> loadHistory(){
        return null;
    }

    @Override
    public boolean nextButtonIsVisible() {
        return false;
    }

    @Override
    public boolean prevButtonIsVisible() {
        return false;
    }

    @Override
    public void setButtonVisibility() {

    }

    @Override
    public void reloadTable() {

    }

    @Override
    public void setUpTable() {
        Vector<String> titles = new Vector<>();
        tableData = new Vector<>();

        titles.add("Proctor Id");
        titles.add("Proctor Name");
        titles.add("Action Type");
        titles.add("Date");

        Vector<Vector<Object>> history = loadHistory();
        try{
            readStatus = !(history.size() == 0);
        }catch (NullPointerException ex){
            readStatus = false;
        }
        displayReadStatus(readStatus);

        stockHistoryTable.setModel(new DefaultTableModel(tableData,titles));
        stockHistoryTable.setDefaultEditor(Object.class,null);//To make each cell none editable.
    }

    public void displayReadStatus(boolean readStatus){
        if(!readStatus) JOptionPane.showMessageDialog(parentComponent,"No history found");
    }

    @Override
    public void addDataToTable(Object object) {

    }

    @Override
    public void refreshTable() {

    }

    @Override
    public void setUpGUi() {
        this.setTitle("StockView Information");
        this.setContentPane(mainPanel);
        this.setSize(new Dimension(WIDTH,HEIGHT));
        this.setLocationRelativeTo(parentComponent);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        backButton.addActionListener(new BackButtonListener(this));

        setUpTable();
        setVisible(true);
    }

    public void goBackToParent(){
        this.dispose();
        parentComponent.setVisible(true);
    }
}
