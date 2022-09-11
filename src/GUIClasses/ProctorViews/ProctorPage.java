package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Proctor;
import BasicClasses.Requests.Request;
import GUIClasses.ActionListeners.ProctorView.DormitoryView.DormListClickListener;
import GUIClasses.ActionListeners.ProctorView.ProctorPage.LogoutMenuItemListener;
import GUIClasses.ActionListeners.ProctorView.ProctorPage.ReportMenuItemListener;
import GUIClasses.ActionListeners.ProctorView.ProctorPage.SeeDormMenuListener;
import GUIClasses.ActionListeners.ProctorView.ProctorPage.SeeStudentMenuListener;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ProctorPage extends JFrame implements Views, TableViews {
    private JPanel MainPanel;
    private JPanel TopPanel;
    private JPanel CentralPanel;
    private JPanel BottomPanel;
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
    private JLabel BlockNumberLabel;
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
            String query = "SELECT * FROM AllReports ORDER BY ReportedDate DESC";
            ResultSet resultSet = javaConnection.selectQuery(query);
            int count = 0;
            try{
                String reporterId;
                while(resultSet.next()){
                    Vector<Object> tmp = new Vector<>();
                    String reportType = resultSet.getString("ReportType");
                    reporterId = resultSet.getString("ReporterId");
                    Date reportedDate = resultSet.getDate("ReportedDate");
                    Date currentDate = Request.getCurrentDate();
                    if(!(reportType.equals("ClothTakeOutForm") & resultSet.getString("ReporterId").equals(reporterId))){
                       if((reportedDate.toString()).equals((currentDate).toString())){
                           tmp.add(++count);
                           tmp.add(reportType);
                           tmp.add(reportedDate);
                           tmp.add(resultSet.getString("BuildingNumber"));
                           tmp.add(resultSet.getString("RoomNumber"));
                           temp.add(tmp);
                       }
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
        String query = "SELECT TOP 1 * FROM ProctorSchedule WHERE PID='"+getProctor().getpId()+"' ORDER BY FromDate ASC,ToDate ASC";
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
        System.out.println("Current date: "+tmp);//For debugging only.
        System.out.println("Date: "+date);//For debugging only.
        return date.after(Request.getCurrentDate());
    }

    public JTable getReportTable(){
        return ReportTable;
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

        Vector<Vector<Object>> temp = loadReports();
        readStatus = !(temp == null);
        addDataToTable(temp);
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


        JMenuBar Services = new JMenuBar();
        Services.setBackground(new Color(72,131,184));
        JMenu Service = new JMenu("Services");
        Service.setForeground(Color.white);

        JMenuItem seeDormitories = new JMenuItem("See Dormitories");
        seeDormitories.setForeground(new Color(72,131,184));
        seeDormitories.addActionListener(new SeeDormMenuListener(this));
        JMenuItem seeStudents = new JMenuItem("See Students");
        seeStudents.setForeground(new Color(72,131,184));
        seeStudents.addActionListener(new SeeStudentMenuListener(this));
        JMenuItem seeReports = new JMenuItem("All Reports");
        seeReports.setForeground(new Color(72,131,184));
        seeReports.addActionListener(new ReportMenuItemListener(this));
        JMenuItem seeStocks = new JMenuItem("See Stocks");
        seeStocks.setForeground(new Color(72,131,184));

        Service.add(seeDormitories);
        Service.add(seeStudents);
        Service.add(seeReports);
        Service.add(seeStocks);

        JMenu logout = new JMenu("Logout");
        logout.setForeground(Color.white);
        JMenuItem signOut = new JMenuItem("Logout");
        signOut.setForeground(new Color(72,131,184));
        signOut.addActionListener(new LogoutMenuItemListener(this));

        logout.add(signOut);

        Services.add(Service);
        Services.add(logout);

        setJMenuBar(Services);

        loadSchedule();

        setVisible(true);
    }
}
