package GUIClasses.ProctorViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Requests.Request;
import GUIClasses.Interfaces.Views;

import javax.swing.*;

public class ReportDetailView extends JFrame implements Views {
    private JPanel mainPanel;
    private JPanel reportDetailPanel;
    private JPanel buttonsPanel;
    private JButton backButton;
    private JButton handleButton;
    private JTextPane descriptionPane;
    private JLabel reporterNameL;
    private JLabel buildingNoL;
    private JLabel dormNoL;
    private Request request;
    private String proctorId;
    private ReportsView parentComponent;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();
    public ReportDetailView(ReportsView parentComponent,Request request, String proctorId){
        this.parentComponent = parentComponent;
        this.request = request;
        this.proctorId = proctorId;
        setUpGUi();
    }
    public void displayReportInfo(){
        /* This method will display all the neccessary report infos in the
        labels and the description pane from the request method.
         */
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Report Detail");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        //Add action listener to the handle button.
    }
}