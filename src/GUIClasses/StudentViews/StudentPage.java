package GUIClasses.StudentViews;

import javax.swing.*;
import java.awt.*;
import BasicClasses.Others.DormMates;

public class StudentPage extends JFrame {
    DormMates dormMates =  new DormMates();
    private JPanel MainPanel;
    JPanel WestBoarder = new JPanel(null);
    private JPanel Center;

    public StudentPage(){

        additionalForms();
        Dormatesinfo();
        this.setTitle("Dormitary Management System - Student");
        this.setContentPane(MainPanel);
        this.setSize(900,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    void additionalForms(){
        //add User main information



        // Menu bar
        JMenuBar Services = new JMenuBar();
        Services.setBackground(new Color(72,131,184));
        JMenu Service = new JMenu("Services");
        Service.setForeground(Color.white);
        MainPanel.add(Services, BorderLayout.NORTH);
        Services.add(Service);
        JMenuItem MaintainanceReport = new JMenuItem("MaintainanceReport");
        MaintainanceReport.setForeground(new Color(72,131,184));
        JMenuItem StayRequest = new JMenuItem("Prolog Dormitary stay request");
        StayRequest.setForeground(new Color(72,131,184));
        JMenuItem RequestForDrom = new JMenuItem("Request for a dorm");
        RequestForDrom.setForeground(new Color(72,131,184));
        JMenuItem SeeRequests = new JMenuItem("See your requests");
        SeeRequests.setForeground(new Color(72,131,184));

        Service.add(MaintainanceReport);Service.add(StayRequest);
        Service.add(RequestForDrom);Service.add(SeeRequests);
    }

    void Dormatesinfo(){
        // add dorm mates info
        
    }
}
