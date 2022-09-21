package GUIClasses.ActionListeners.ProctorView.StudentView;

import GUIClasses.ProctorViews.StudentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class FilterButtonListener implements ActionListener {
    private StudentView parentComponent;
    public FilterButtonListener(StudentView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.reloadTable();
        String query = getFilteringQuery();
        filterStudents(query);

        Vector<Vector<Object>> filteredStudents = parentComponent.getTableData();

        if(filteredStudents.size() == 0 ){
            if(parentComponent.getSelectedCondition().equals("Not Eligible"))
                JOptionPane.showMessageDialog(parentComponent,"All are eligible students");
            else
                JOptionPane.showMessageDialog(parentComponent,"No students matching the condition");
            parentComponent.reloadTable();
        }
    }

    public String getFilteringQuery(){
        String selectedCondition = parentComponent.getSelectedCondition();
        String query = "";
        if(selectedCondition.equals("Year of students")){
            query = "SELECT * FROM Student WHERE year = "+parentComponent.getFilterInputText()+
                    " ORDER BY (SELECT NULL) OFFSET "
                    +(parentComponent.getPageNumber()-1)*parentComponent.getRowPerPage()+
                    " ROWS FETCH NEXT "+parentComponent.getRowPerPage()+" ROWS ONLY";
        } else if(selectedCondition.equals("Block")){
            query = "SELECT * FROM Student WHERE BuildingNumber = '"+parentComponent.getFilterInputText()+
                    "' ORDER BY (SELECT NULL) OFFSET "
                    +(parentComponent.getPageNumber()-1)*parentComponent.getRowPerPage()+
                    " ROWS FETCH NEXT "+parentComponent.getRowPerPage()+" ROWS ONLY";
        } else if(selectedCondition.equals("Not Eligible")){
            query = "SELECT * FROM Student WHERE isEligible = 0 ORDER BY (SELECT NULL) OFFSET "
                    +(parentComponent.getPageNumber()-1)*parentComponent.getRowPerPage()+
                    " ROWS FETCH NEXT "+parentComponent.getRowPerPage()+" ROWS ONLY";
        } else{
            query = "SELECT * FROM Student ORDER BY (SELECT NULL) OFFSET "
                    +(parentComponent.getPageNumber()-1)*parentComponent.getRowPerPage()+
                    " ROWS FETCH NEXT "+parentComponent.getRowPerPage()+" ROWS ONLY";
        }
        return  query;
    }

    public void filterStudents(String query){
        parentComponent.reloadTable(query);
    }
}
