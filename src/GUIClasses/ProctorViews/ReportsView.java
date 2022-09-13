package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import GUIClasses.ActionListeners.ProctorView.AllReportBackButtonListener;
import GUIClasses.ActionListeners.ProctorView.ProctorPage.ReportDetailClickListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public JTable getReportTable() {
        return reportList;
    }

    public String getProctorId(){
        return proctorId;
    }

    public void refreshParentTable(){
        Vector<Vector<Object>> tmp = parentComponent.loadReports();
        parentComponent.refreshTable(tmp);
    }
    public Vector<Vector<Object>> loadReports(){
        /* This method will load all unhandled reports (handled date is null)
        from the dataBase and add them to the table data.
         */
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        Vector<Vector<Object>> temp = null;
        if(javaConnection.isConnected()){
            temp = new Vector<>();
            String query = "SELECT * FROM AllReports WHERE HandledDate IS NULL ORDER BY ReportedDate DESC";
            ResultSet resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    String reportType = resultSet.getString("ReportType");
                    int reportId = resultSet.getInt("ReportId");
                    Date reportedDate = resultSet.getDate("ReportedDate");

                    Vector<Object> tmp = new Vector<>();
                    tmp.add(reportId);
                    tmp.add(reportType);
                    tmp.add(reportedDate);
                    tmp.add(resultSet.getString("BuildingNumber"));
                    tmp.add(resultSet.getString("RoomNumber"));
                    temp.add(tmp);
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
                JOptionPane.showMessageDialog(this,"Sorry error in loading the reports from server");
            }
        }
        return temp;
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

        tableData = loadReports();
        readStatus = !(tableData == null);//If the read is not successful the table data will be null.
        reportList.setModel(new DefaultTableModel(tableData,titles));
        reportList.setDefaultEditor(Object.class,null);//Making every row non-editable
        reportList.addMouseListener(new ReportDetailClickListener(this));
        refreshTable();
    }

    public void refreshTable(Vector<Vector<Object>> tableData){
        this.tableData.clear();
        readStatus = !(tableData == null);
        addDataToTable(tableData);
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
