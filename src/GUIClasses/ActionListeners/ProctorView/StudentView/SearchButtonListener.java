package GUIClasses.ActionListeners.ProctorView.StudentView;

import BasicClasses.Others.JavaConnection;
import GUIClasses.ProctorViews.StudentView;

import javax.naming.ldap.PagedResultsControl;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.StubNotFoundException;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.jar.JarOutputStream;

public class SearchButtonListener implements ActionListener {
    private StudentView parentComponent;
    public SearchButtonListener(StudentView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.reloadTable();
        Vector<Vector<Object>> students = searchStudent();
        if(students.size() == 0) {
            JOptionPane.showMessageDialog(parentComponent,"Couldn't find the student");
            return;
        } else{
            parentComponent.getTableData().clear();
            parentComponent.addDataToTable(students);
            parentComponent.refreshTable();
        }
    }

    public Vector<Vector<Object>> searchStudent(){
        String query = "SELECT * FROM Student WHERE SID='"+ parentComponent.getSearchText()+"'";
        Vector<Vector<Object>> students;
        students = parentComponent.loadStudents(query);
        if(students.size() == 0){
            query = "SELECT * FROM Student WHERE Fname+' '+lname LIKE '%"+parentComponent.getSearchText()+"%'";//loading students with name containing in the search text field.
            students = parentComponent.loadStudents(query);
        }
        return students;
    }
}
