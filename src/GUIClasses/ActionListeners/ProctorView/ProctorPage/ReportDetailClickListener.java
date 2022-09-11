package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import BasicClasses.Requests.Request;
import GUIClasses.ProctorViews.ProctorPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ReportDetailClickListener implements MouseListener {
    private ProctorPage parentComponent;

    public ReportDetailClickListener(JFrame parentComponent){
        this.parentComponent = (ProctorPage) parentComponent;
    }

    public Request getRequest(){
        JTable table = parentComponent.getReportTable();
        int clickedRow;
        try{
            clickedRow = table.getSelectedRow();
        } catch (IndexOutOfBoundsException ex){
            clickedRow = 0;
        }

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
