package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.ProctorView.StockView.BackButtonListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;
import GUIClasses.TableViewPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StockView extends TableViewPage implements Views, TableViews {
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
    private JButton nextButton;
    private JButton prevButton;
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
        Vector<Vector<Object>> history = new Vector<>();
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet;
        String query = "SELECT * FROM Proctor AS P JOIN ProctorControlsStock AS PCS ON P.EID = PCS.EID WHERE PCS.buildingNumber='"+proctor.getBuildingNo()+
                "' ORDER BY actionDate DESC OFFSET "+(getPageNumber()-1)*ROW_PER_PAGE+" ROWS FETCH NEXT "+ROW_PER_PAGE+" ROWS ONLY";
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    Vector<Object> tmp = new Vector<>();
                    tmp.add(resultSet.getString("EID"));
                    tmp.add(resultSet.getString("Fname")+" "+resultSet.getString("Lname"));
                    tmp.add(resultSet.getString("actionType"));
                    tmp.add(resultSet.getString("actionDate"));
                    history.add(tmp);
                }
            }catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return history;
    }

    @Override
    public boolean nextButtonIsVisible() {
        boolean hasNext = getPageNumber()<getTotalPage();
        return hasNext;
    }

    @Override
    public boolean prevButtonIsVisible() {
        boolean hasPrev = getPageNumber()>1;
        return hasPrev;
    }

    @Override
    public void setButtonVisibility() {
        boolean visibility = nextButtonIsVisible();
        nextButton

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
        addDataToTable(history);
        refreshTable();
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
        Vector<Vector<Object>> history = (Vector<Vector<Object>>) object;
        tableData = history;
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) stockHistoryTable.getModel();
        tableModel.fireTableDataChanged();
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
