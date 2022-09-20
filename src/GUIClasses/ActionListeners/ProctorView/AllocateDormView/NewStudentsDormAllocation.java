package GUIClasses.ActionListeners.ProctorView.AllocateDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.Request;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ActionListeners.ProctorView.ProgressBarListener;
import GUIClasses.ProctorViews.DormitoryView;
import GUIClasses.TableViewPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class NewStudentsDormAllocation extends ProgressBarListener {
    public NewStudentsDormAllocation(DormitoryView parentComponent){
        this.parentComponent = parentComponent;

        availableDorms = new ArrayList<>();
        students = new HashMap<>();
    }

    public void setRemainingStudents(int remainingStudents) {
        this.remainingStudents = remainingStudents;
    }

    public void setTotalSpace(int totalSpace) {
        this.totalSpace = totalSpace;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean updateStatus;
        String query;
        String query2 = "SELECT COUNT(*) AS TotalNo FROM AvailableDorm";

        loadAndSetTotalPage(query2);
        resetPageNumber();
        students.clear();

        allocateStudents();

        query = "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Allocate Dorm', '"+
                Request.getCurrentDate()+"' , '"+parentComponent.getProctor().getBuildingNo()+"')";

        insertHistory(query);
    }
    public boolean allocateAllStudents(){
        String query;
        boolean updateStatus;
        query = "SELECT COUNT(*) AS TotalNo FROM STUDENT WHERE BuildingNumber IS NULL AND RoomNumber IS NULL " +
                "AND Place != 'ADDIS ABABA' AND isEligible = 1 ";
        remainingStudents = getTotalStudentNo(query);
        do{

            loadAvailableDorms();
            sortDormOnBuildingNo();
            totalSpace = getTotalSpace();

            System.out.println("TotalSpace: "+totalSpace);

            loadNewStudents();
            updateStatus = allocate();
            incrementPageNumber();
            if(getPageNumber()>getTotalPage()) resetPageNumber();
            updateDormInfo();
        }while(remainingStudents>0);
        return updateStatus;
    }

    public void insertHistory(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        if(javaConnection.isConnected()){
            javaConnection.insertQuery(query);
        }
    }
}
