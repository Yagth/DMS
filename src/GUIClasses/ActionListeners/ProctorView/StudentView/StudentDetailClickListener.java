package GUIClasses.ActionListeners.ProctorView.StudentView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import GUIClasses.ProctorViews.StudentDetailView;
import GUIClasses.ProctorViews.StudentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDetailClickListener implements MouseListener {
    private StudentView parentComponent;

    public StudentDetailClickListener(StudentView parentComponent) {
        this.parentComponent = parentComponent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String SID = getClickedRowSID();
        Student student = loadStudent(SID);
        new StudentDetailView(student,parentComponent);
    }

    public String getClickedRowSID(){
        JTable table  = parentComponent.getTable();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int row;
        String SID;
        try{
            row = table.getSelectedRow();
            SID = (String) tableModel.getValueAt(row,1);
        } catch (ArrayIndexOutOfBoundsException ex){
            row = 0;
            SID = (String) tableModel.getValueAt(row,1);
        }
        return SID;
    }

    public Student loadStudent(String SID){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM Student WHERE SID='"+SID+"'";
        ResultSet resultSet;
        Student student = null;

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    student = new Student(resultSet.getString("Fname"),
                                        resultSet.getString("Lname"),
                                        SID,
                                        resultSet.getString("Gender")
                                );
                    student.setDepartment(resultSet.getString("Department"));
                    int pillow = resultSet.getInt("pillowNo");
                    int bedBase = resultSet.getInt("bedBaseNo");
                    int matress = resultSet.getInt("MatressNo");

                    boolean hasAllEquipments = (pillow == 1 & bedBase == 1 & matress == 1);
                    boolean isEligible = (resultSet.getInt("isEligible") == 1);

                    student.setHasAllEquipments(hasAllEquipments);
                    student.setEligibility(isEligible);
                    student.setPlaceOfOrigin(resultSet.getString("Place"));
                    student.setYear(resultSet.getInt("Year"));
                    student.setDormNo(resultSet.getString("RoomNumber"));
                    student.setBuildingNo(resultSet.getString("BuildingNumber"));
                }
            } catch (SQLException ex){
                ex.printStackTrace(); //For debugging only.
            }
        }
        return student;
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
