package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.Request;
import GUIClasses.ProctorViews.ProctorPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

public class ReportDetailClickListener implements MouseListener {
    private ProctorPage parentComponent;

    public ReportDetailClickListener(JFrame parentComponent){
        this.parentComponent = (ProctorPage) parentComponent;
    }

    public Request getRequest(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        ResultSet resultSet;
        JTable table = parentComponent.getReportTable();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int clickedRow = table.getSelectedRow();
        int ReportId = (int) tableModel.getValueAt(clickedRow,0);
        String query;


        if(javaConnection.isConnected()){

        }
        return null;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
