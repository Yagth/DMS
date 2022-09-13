package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import GUIClasses.ActionListeners.ProctorView.AllReportBackButtonListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class ReportsView extends JFrame implements Views, TableViews {
    private JPanel mainPanel;
    private JPanel reportListPanel;
    private JPanel butttonsPanel;
    private JTable reportList;
    private JButton nextButton;
    private JButton backButton;
    private ProctorPage parentComponent;
    private String proctorId;
    private Vector<Vector<Object>> tableData;
    private boolean readStatus;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public ReportsView(ProctorPage parentComponent, String proctorId){
        this.parentComponent = parentComponent;
        this.proctorId = proctorId;
        setUpGUi();
        displayReadStatus(readStatus);
    }

    public void loadReports(){
        /* This method will load all unhandled reports (handled date is null)
        from the dataBase and add them to the table data.
         */
    }
    public void displayReadStatus(boolean readStatus){
        if(!readStatus)
            JOptionPane.showMessageDialog(this,"Couldn't read the emergency contacts due to connection error"
                    ,"Reading Error",JOptionPane.ERROR_MESSAGE);
    }

    public void showParentComponent(){
        this.parentComponent.setVisible(true);
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Reports");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.
        setUpTable();
        backButton.addActionListener(new AllReportBackButtonListener(this));
        this.setVisible(true);
    }


    @Override
    public void setUpTable() {
        Vector<String> titles = new Vector<>();
        titles.add("Report No");
        titles.add("Report Type");
        titles.add("Reported Date");
        titles.add("Building No");
        titles.add("Room No");

        loadReports();
        readStatus = !(tableData == null);//If the read is not successful the table data will be null.
        reportList.setModel(new DefaultTableModel(tableData,titles));
        refreshTable();
    }

    @Override
    public void addDataToTable(Object object) {
        // No need to implement this method.
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) reportList.getModel();
        tableModel.fireTableDataChanged();
    }
}
