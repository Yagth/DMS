package GUIClasses.ActionListeners.ProctorView.StudentView;

import GUIClasses.ProctorViews.StudentView;

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
        Vector<Vector<Object>> filteredStudents = filterStudents();
        parentComponent.getTableData().clear();
        parentComponent.addDataToTable(filteredStudents);
        parentComponent.refreshTable();
    }

    public int getFilteringColumn(){
        String selectedCondition = parentComponent.getSelectedCondition();
        if(selectedCondition.equals("Year of students")) return 3; //This is the column index for the year in the table.
        else if(selectedCondition.equals("Block")) return 4; //This is the column index for the buildingNumber in the table.
        else return 5; //This is the column index for the eligibility in the table.
    }

    public Vector<Vector<Object>> filterStudents(){
        Vector<Vector<Object>> students = parentComponent.getTableData();
        System.out.println("Size of students: "+students.size());//For debugging only.
        Vector<Vector<Object>> tmp = new Vector<>();
        Object comparingObject = parentComponent.getFilterInputText();
        int index = getFilteringColumn();

        for(Vector<Object> student : students){
            if(student.get(index).equals(comparingObject))
                tmp.add(student);
        }
        return tmp;
    }
}
