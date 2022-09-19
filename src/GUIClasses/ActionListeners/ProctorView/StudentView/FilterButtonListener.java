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

        Vector<Vector<Object>> filteredStudents = filterStudents();
        parentComponent.getTableData().clear();
        parentComponent.addDataToTable(filteredStudents);
        parentComponent.refreshTable();

        if(filteredStudents.size() == 0 ){
            if(parentComponent.getSelectedCondition().equals("Eligibility"))
                JOptionPane.showMessageDialog(parentComponent,"No eligible students found");
            else
                JOptionPane.showMessageDialog(parentComponent,"No students matching the condition");
            parentComponent.reloadTable();
        }
    }

    public int getFilteringColumn(){
        String selectedCondition = parentComponent.getSelectedCondition();
        if(selectedCondition.equals("Year of students")) return 3; //This is the column index for the year in the table.
        else if(selectedCondition.equals("Block")) return 4; //This is the column index for the buildingNumber in the table.
        else return 5; //This is the column index for the eligibility in the table.
    }

    public Vector<Vector<Object>> filterStudents(){
        Vector<Vector<Object>> students = parentComponent.getTableData();
        Vector<Vector<Object>> tmp = new Vector<>();
        int index = getFilteringColumn();
        if(parentComponent.getSelectedCondition().equals("Eligibility")){
            for(Vector<Object> student : students){
                if(student.get(index).toString().equals("true"))
                    tmp.add(student);
            }
        }
        else{
            Object comparingObject = parentComponent.getFilterInputText();


            for(Vector<Object> student : students){
                try{
                    if(comparingObject.equals(student.get(index).toString())){
                        tmp.add(student);
                    }
                } catch (NullPointerException ex){
                    ex.printStackTrace();//For debugging only.
                }

            }
        }
        return tmp;
    }
}
