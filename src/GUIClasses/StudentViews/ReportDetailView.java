package GUIClasses.StudentViews;

import BasicClasses.Enums.SizeOfMajorClasses;
import BasicClasses.Requests.*;
import GUIClasses.ActionListeners.ReportDetailViewBackButtonListener;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private Request request;
    private String reporterId;
    private static final int WIDTH = SizeOfMajorClasses.WIDTH.getSize();
    private static final int HEIGHT = SizeOfMajorClasses.HEIGHT.getSize();

    private SeeYourRequests parentComponent;
    public ReportDetailView(SeeYourRequests parentComponent,Request request,String reporterId){
        this.parentComponent = parentComponent;
        this.request = request;
        this.reporterId = reporterId;
        setUpGUi();
    }
    public void displayRequest(){
        boolean isHandled = checkReportStatus();
        reportTypeL.setText(request.getRequestType());
        reportedDateL.setText(String.valueOf(request.getRequestedDate()));
        descriptionPane.setText(request.getDescription());
        if(isHandled){
            reportStatusL.setText("Handled");
            reportHandledDate.setText(String.valueOf(request.getHandledDate()));
        }
        else{
            reportStatusL.setText("On Queue");
            reportHandledDate.setVisible(false);
            handledDate.setVisible(false);
        }

        boolean hasNoLocation = request.getLocation()==null;

        if(hasNoLocation){
            locationL.setVisible(false);
            reportLocationL.setVisible(false);
        }
        else{
            reportLocationL.setText(request.getLocation());
        }

    }

    public boolean checkReportStatus(){
        return !(request.getHandledDate()==null);
    }
    public SeeYourRequests getParentComponent(){
        return parentComponent;
    }

    public void showParentComponent(){
        parentComponent.setVisible(true);
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Request Detail");
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(parentComponent);
        this.setSize(WIDTH,HEIGHT);
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
        backButton.addActionListener(new ReportDetailViewBackButtonListener(this));
        displayRequest();
    }
}
