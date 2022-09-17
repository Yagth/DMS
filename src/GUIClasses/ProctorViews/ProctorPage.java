package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import BasicClasses.Requests.Request;
import GUIClasses.ActionListeners.NextActionListener;
import GUIClasses.ActionListeners.PrevActionListener;
import GUIClasses.ActionListeners.ProctorView.ProctorPage.*;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;
import GUIClasses.TableViewPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ProctorPage extends TableViewPage implements Views, TableViews {
    private JPanel MainPanel;
    private JPanel CentralPanel;
    private JPanel BottomPanel;
    private JButton prevButton;
    private JButton nextButton;
    private JPanel SchedulePanel;
    private JPanel ReportPanel;
    private JTable ReportTable;
    private JPanel ReportsMTpanel;
    private JScrollPane ReportScrollPane;
    private JPanel ScheduleBodyPanel;
    private JLabel blockNumberL;
    private JLabel toDateL;
    private JLabel fromDateL;
    private Vector<Vector<Object>> tableData;
    private Proctor proctor;
    private boolean readStatus;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    public ProctorPage(Proctor proctor){
        this.proctor = proctor;

        String query = "SELECT Count(*) AS TotalNo FROM AllReports WHERE HandledDate IS NULL";
        loadAndSetTotalPage(query);

        setUpGUi();
        setUpTable();
    }
    public Proctor getProctor(){
        return proctor;
    }

    public Vector<Vector<Object>> loadReports(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        Vector<Vector<Object>> temp = null;
        if(javaConnection.isConnected()){
            temp = new Vector<>();
            String query = "SELECT * FROM AllReports WHERE HandledDate IS NULL ORDER BY ReportedDate DESC " +
                    "OFFSET "+ (getPageNumber()-1)*ROW_PER_PAGE+ " ROWS FETCH NEXT "+ROW_PER_PAGE+" ROWS ONLY";
            ResultSet resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    String reportType = resultSet.getString("ReportType");
                    int reportId = resultSet.getInt("ReportId");
                    Date reportedDate = resultSet.getDate("ReportedDate");
                    Date currentDate = Request.getCurrentDate();

                    if((reportedDate.toString()).equals((currentDate).toString())){
                        Vector<Object> tmp = new Vector<>();
                        tmp.add(reportId);
                        tmp.add(reportType);
                        tmp.add(reportedDate);
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

    public void setScheduleLabels(Date fromDate, Date toDate, String buildingNumber){
        this.fromDateL.setText(fromDate.toString());
        this.toDateL.setText(toDate.toString());
        this.blockNumberL.setText(buildingNumber);
    }

    public void loadSchedule(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT TOP 1 * FROM ProctorSchedule WHERE PID='"+getProctor().getpId()+
                "' ORDER BY FromDate ASC,ToDate ASC";
        Date fromDate = null, toDate = null;
        String buildingNumber = "";
        ResultSet resultSet;

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    fromDate = resultSet.getDate("FromDate");
                    toDate = resultSet.getDate("ToDate");
                    buildingNumber = resultSet.getString("BuildingNumber");
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
            if(toDateIsValid(toDate)){
                setScheduleLabels(fromDate,toDate,buildingNumber);
            }

        }
    }

    public boolean toDateIsValid(Date date){
        Date tmp = Request.getCurrentDate();//For debugging only.
        boolean isValid = false;
        try{
            isValid = date.after(Request.getCurrentDate());
        } catch (NullPointerException ex){
            ex.printStackTrace();//For debugging only.
        }
        return isValid;
    }

    public JTable getReportTable(){
        return ReportTable;
    }

    public Vector<Vector<Object>> getTableData() {
        return tableData;
    }

    @Override
    public boolean nextButtonIsVisible() {
        boolean hasNext = getPageNumber()<getTotalPage();
        return  hasNext;
    }

    @Override
    public boolean prevButtonIsVisible() {
        boolean hasPrev = getPageNumber()>1;
        return  hasPrev;
    }

    @Override
    public void setButtonVisibility() {
        boolean visibility = nextButtonIsVisible();
        nextButton.setVisible(visibility);
        visibility = prevButtonIsVisible();
        prevButton.setVisible(visibility);

        this.revalidate();
    }

    @Override
    public void reloadTable() {
        Vector<Vector<Object>> temp = loadReports();
        refreshTable(temp);
        refreshTable();
    }

    @Override
    public void setUpTable() {
        Vector<Object> titles = new Vector<>();
        tableData = new Vector<>();
        titles.add("Report Number");
        titles.add("Report Type");
        titles.add("Reported Date");
        titles.add("Building Number");
        titles.add("Room Number");

        ReportTable.setModel(new DefaultTableModel(tableData,titles));
        ReportTable.setDefaultEditor(Object.class,null);
        ReportTable.addMouseListener(new ReportDetailClickListener(this));

        Vector<Vector<Object>> temp = loadReports();
        refreshTable(temp);

    }

    public void refreshTable(Vector<Vector<Object>> tableData){
        this.tableData.clear();
        readStatus = !(tableData == null);
        addDataToTable(tableData);
        refreshTable();
    }

    @Override
    public void addDataToTable(Object object) {
        Vector<Vector<Object>> temp = (Vector<Vector<Object>>) object;
        for(int i = 0; i<temp.size();i++){
            tableData.add(temp.get(i));
        }
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
        this.getContentPane().setBackground(new Color(232,255,255));

        nextButton.addActionListener(new NextActionListener(this));
        prevButton.addActionListener(new PrevActionListener(this));

        ImageIcon tmp = new ImageIcon("Images/AAULOGOSmall.png");
        Image titleLogo = tmp.getImage();

        this.setIconImage(titleLogo);

        JMenuBar Services = new JMenuBar();
        Services.setBackground(new Color(232,255,255));
        Services.setOpaque(true);

        JMenu Service = new JMenu("Services");
        Service.setForeground(Color.GRAY);

        JMenuItem seeDormitories = new JMenuItem("See Dormitories");
        seeDormitories.setForeground(Color.BLACK);
        seeDormitories.addActionListener(new SeeDormMenuListener(this));
        JMenuItem seeStudents = new JMenuItem("See Students");
        seeStudents.setForeground(Color.BLACK);
        seeStudents.addActionListener(new SeeStudentMenuListener(this));
        JMenuItem seeReports = new JMenuItem("All Reports");
        seeReports.setForeground(Color.BLACK);
        seeReports.addActionListener(new ReportMenuItemListener(this));
        JMenuItem seeStocks = new JMenuItem("See Stocks");
        seeStocks.addActionListener(new StockMenuItemListener(this));
        seeStocks.setForeground(Color.BLACK);

        Service.add(seeDormitories);
        Service.add(seeStudents);
        Service.add(seeReports);
        Service.add(seeStocks);

        JMenu logout = new JMenu("Logout");
        logout.setForeground(Color.GRAY);
        JMenuItem signOut = new JMenuItem("Logout");
        signOut.setForeground(Color.BLACK);
        signOut.addActionListener(new LogoutMenuItemListener(this));

        logout.add(signOut);

        Services.add(Service);
        Services.add(logout);

        setJMenuBar(Services);

        loadSchedule();
        setButtonVisibility();

        setVisible(true);
    }
}
