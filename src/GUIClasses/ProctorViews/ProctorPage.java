package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import GUIClasses.ActionListeners.StudentPage.ClothTakeOutMenuItemListener;
import GUIClasses.ActionListeners.StudentPage.ExtendDormMenuItemListener;
import GUIClasses.ActionListeners.StudentPage.LogoutMenuItemListener;
import GUIClasses.ActionListeners.StudentPage.MaintenanceMenuItemListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ProctorPage extends JFrame implements Views, TableViews {
    private JPanel MainPanel;
    private JPanel TopPanel;
    private JPanel CentralPanel;
    private JPanel BottomPanel;
    private JButton StockButton;
    private JButton DormButton;
    private JButton StudentButton;
    private JButton ReportButton;
    private JButton PrevButton;
    private JButton NextButton;
    private JPanel SchedulePanel;
    private JPanel ReportPanel;
    private JTable ReportTable;
    private JPanel ReportsMTpanel;
    private JLabel ReportsMTlabel;
    private JScrollPane ReportScrollPane;
    private JPanel ScheduleHeadline;
    private JLabel ScheduleHeadLineText;
    private JPanel ScheduleBodyPanel;
    private JTextField BlockNumberText;
    private JTextField DateText;
    private JTextField OtherProctorText;
    private JLabel BlockNumberLabel;
    private JLabel DateLabel;
    private JLabel OtherProctorLabel;
    private Vector<Vector<Object>> tableData;
    private Proctor proctor;
    private boolean readStatus;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public ProctorPage(Proctor proctor){
        this.proctor = proctor;
        setUpGUi();
        setUpTable();
        refreshTable();
    }
    public ProctorPage(){
        this(null);
    }//For debugging purposes.

    public Vector<Vector<Object>> loadReports(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        Vector<Vector<Object>> temp = null;
        if(javaConnection.isConnected()){
            temp = new Vector<>();
            String query = "SELECT * FROM AllReports ORDER BY ReportedDate DESC";
            ResultSet resultSet = javaConnection.selectQuery(query);
            int count = 0;
            try{
                String reporterId;
                while(resultSet.next()){
                    Vector<Object> tmp = new Vector<>();
                    String reportType = resultSet.getString("ReportType");
                    reporterId = resultSet.getString("ReporterId");

                    if(!(reportType.equals("ClothTakeOutForm") & resultSet.getString("ReporterId").equals(reporterId))){
                        tmp.add(++count);
                        tmp.add(reportType);
                        tmp.add(resultSet.getString("ReportedDate"));
                        tmp.add(resultSet.getString("BuildingNumber"));
                        tmp.add(resultSet.getString("RoomNumber"));
                        temp.add(tmp);
                    }
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
                JOptionPane.showMessageDialog(this,"Sorry error in loading the reports from server");
            }
        }
        return temp;
    }
    @Override
    public void setUpTable() {
        Vector<Object> titles = new Vector<>();
        titles.add("Report Number");
        titles.add("Report Type");
        titles.add("Reported Date");
        titles.add("Building Number");
        titles.add("Room Number");

        Vector<Vector<Object>> temp = loadReports();
        readStatus = !(temp == null);
        addDataToTable(temp);

        ReportTable.setModel(new DefaultTableModel(tableData,titles));
    }

    @Override
    public void addDataToTable(Object object) {
        Vector<Vector<Object>> temp = (Vector<Vector<Object>>) object;
        tableData = temp;
    }

    @Override
    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) ReportTable.getModel();
        tableModel.fireTableDataChanged();
    }

    @Override
    public void setUpGUi() {
        setTitle("Home page");
        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);

        JMenuBar Services = new JMenuBar();
        Services.setBackground(new Color(72,131,184));
        JMenu Service = new JMenu("Services");
        Service.setForeground(Color.white);

        JMenuItem seeDormitories = new JMenuItem("See Dormitories");
        seeDormitories.setForeground(new Color(72,131,184));
        JMenuItem seeStudents = new JMenuItem("See Students");
        seeStudents.setForeground(new Color(72,131,184));
        JMenuItem seeStocks = new JMenuItem("See Stocks");
        seeStocks.setForeground(new Color(72,131,184));

        JMenu logout = new JMenu("Logout");
        logout.setForeground(Color.white);
        JMenuItem signOut = new JMenuItem("Logout");
        signOut.setForeground(new Color(72,131,184));

        logout.add(signOut);
        Services.add(logout);

        setJMenuBar(Services);
        setVisible(true);
    }
}
