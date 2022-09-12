package GUIClasses;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Others.Cloth;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.*;
import GUIClasses.ActionListeners.ReportDetailViewBackButtonListener;
import GUIClasses.Interfaces.Views;
import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.StudentViews.SeeYourRequests;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class ReportDetailView extends JFrame implements Views {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel requestInfoPanel;
    private JLabel typeL;
    private JLabel dateL;
    private JLabel reportTypeL;
    private JLabel reportedDateL;
    private JLabel reportStatusL;
    private JLabel statusL;
    private JLabel reportLocationL;
    private JLabel locationL;
    private JTextPane descriptionPane;
    private JLabel descriptionLabel;
    private JPanel buttonsPanel;
    private JButton backButton;
    private JLabel handledDate;
    private JLabel reportHandledDate;
    private JLabel reporterNameL;
    private JLabel reporterName;
    private String nameOfReporter;
    private Request request;
    private JLabel reporterIdL;
    private JLabel Reporter;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    private JFrame parentComponent;
    public ReportDetailView(JFrame parentComponent,Request request,String reporterName){
        this.parentComponent = parentComponent;
        this.request = request;
        nameOfReporter = reporterName;
        setUpGUi();
    }
    public void displayRequest(){
        ArrayList<ClothTakeOutRequest> clothRequests = getClothReport();
        boolean isHandled = checkReportStatus();
        reportTypeL.setText(request.getRequestType());
        reportedDateL.setText(String.valueOf(request.getRequestedDate()));

        if(request.getRequestType().equals("ClothTakeOutForm")){
            String description = "";
            for(ClothTakeOutRequest tmp : clothRequests){
                description += tmp.getDescription()+"\n";
                System.out.println("ClothName: "+tmp.getDescription());//For debugging purpose.
            }
            descriptionPane.setText(description);
            Reporter.setText(request.getRequesterId());
            reporterName.setText(nameOfReporter);
        } else{
            descriptionPane.setText(request.getDescription());
        }

        if(isHandled){
            reportStatusL.setText("Handled");
            reportHandledDate.setText(String.valueOf(request.getHandledDate()));
        } else{
            reportStatusL.setText("On Queue");
            reportHandledDate.setVisible(false);
            handledDate.setVisible(false);
        }

        boolean hasNoLocation = request.getLocation().equals("null-null");

        if(hasNoLocation){
            locationL.setVisible(false);
            reportLocationL.setVisible(false);
        }
        else{
            reportLocationL.setText(request.getLocation());
        }

    }

    public ArrayList<ClothTakeOutRequest> getClothReport(){
        ArrayList<ClothTakeOutRequest> clothReportList = new ArrayList<>();
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT ReportCount FROM ClothStudent WHERE ReportId="+request.getRequestId();
        ResultSet resultSet;
        int count = 0;
        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            System.out.println("Query: "+query);//For debugging only.
            try{
                while(resultSet.next()){
                    resultSet.getInt("ReportCount");
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        javaConnection = new JavaConnection(JavaConnection.URL);
        query = "SELECT * FROM ClothStudent WHERE ReportCount="+count;

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    String reporterId = resultSet.getString("ReporterId");
                    String description = resultSet.getString("Description");
                    System.out.println("Description: "+description);//For debugging only.

                    ClothTakeOutRequest tmp = new ClothTakeOutRequest(reporterId,count);
                    tmp.setDescription(description);
                    System.out.println("Query1: "+query);//For debugging only.
                    clothReportList.add(tmp);
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
        }
        return  clothReportList;
    }

    public boolean checkReportStatus(){
        return !(request.getHandledDate()==null);
    }
    public JFrame getParentComponent(){
        return parentComponent;
    }

    public void showParentComponent(){
        parentComponent.setVisible(true);
    }
    public boolean isInProctorView(){
        try{
            SeeYourRequests parentComponent = (SeeYourRequests) this.parentComponent;
            return false;
        } catch (ClassCastException ex){
            return true;
        }
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Request Detail");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(parentComponent);
        backButton.addActionListener(new ReportDetailViewBackButtonListener(this));
        displayRequest();
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                parentComponent.setVisible(true);
            }
        }); //A custom action listener for the exit button.

        boolean isInProctorView = isInProctorView();
        reporterIdL.setVisible(isInProctorView);
        Reporter.setVisible(isInProctorView);
        reporterName.setVisible(isInProctorView);
        reporterNameL.setVisible(isInProctorView);

    }
}
