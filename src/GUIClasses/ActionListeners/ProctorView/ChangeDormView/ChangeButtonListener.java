package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.Request;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ActionListeners.ProctorView.ProgressBarListener;
import GUIClasses.ProctorViews.ChangeDormView;
import com.microsoft.sqlserver.jdbc.JaasConfiguration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

public class ChangeButtonListener extends ProgressBarListener {
    private ChangeDormView parentComponent;
    private int totalStudents;
    private ArrayList<ArrayList<Student>> groupOfStudents; //Collection of students that are in the same dorm.

    public ChangeButtonListener(ChangeDormView parentComponent){
        this.parentComponent = parentComponent;
        availableDorms = new ArrayList<>();
        groupOfStudents = new ArrayList<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        boolean updateStatus = false;
        String condition = parentComponent.getSelectedCondition();
        String fromBuildingNo = parentComponent.getBuildingNo();
        String toBuildingNo = parentComponent.getDestinationBuildingNo();
        String toRoomNo = parentComponent.getDestinationRoomNo();
        Student student = parentComponent.getStudent();
        int numberOfStudents = 0;
        String query ="";
        if(toBuildingNo.equals("")){
            JOptionPane.showMessageDialog(parentComponent,"Destination building is empty",
                    "Empty message",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(condition.equals("Change single student")){
            numberOfStudents = getDormStudentsNo(student);
        }

        int choice = JOptionPane.showConfirmDialog(parentComponent,
                "Are you sure you want to change the students?","Confirm Change",
                JOptionPane.YES_NO_OPTION);

        if(choice == 1) return; //If the choice is no, none of the changing process will be done.

        if(condition.equals("Change single student")){
            if(toRoomNo.equals("")){
                JOptionPane.showMessageDialog(parentComponent,"Destination dorm number is empty",
                        "Empty message",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int maxCapacity = getDormMaxCapacity(toBuildingNo,toRoomNo);


            if(maxCapacity == 0){
                JOptionPane.showMessageDialog(parentComponent,"Couldn't locate the dorm. Check the dorm and building number.",
                        "Dorm doesn't exist",JOptionPane.ERROR_MESSAGE);
                return; //Aborts the change if not enough space by returning from action performed method.

            }

            if(numberOfStudents>=maxCapacity){
                JOptionPane.showMessageDialog(parentComponent,"There is not enough space in the dorm aborting change.",
                        "Not enough space",JOptionPane.ERROR_MESSAGE);
                return; //Aborts the change if not enough space by returning from action performed method.
            }
            updateStatus = changeStudent(student.getsId(),toBuildingNo,toRoomNo);

        }
        else {
            int totalSpace = getAvailableSpaceOnBuilding();
            remainingStudents = getTotalStudents(fromBuildingNo);
            if(fromBuildingNo.equals("")){
                JOptionPane.showMessageDialog(parentComponent,"From building is empty",
                        "Empty message",JOptionPane.ERROR_MESSAGE);
                return;
            } else if(totalSpace<remainingStudents){
                int confirm = JOptionPane.showConfirmDialog(parentComponent,"Some students may be left out because of lack of space, proceed?",
                        "Empty message",JOptionPane.YES_NO_OPTION);
                if(confirm == 1) return;
            }

            if(javaConnection.isConnected()){
                changeAllStudents(toBuildingNo,fromBuildingNo);
            }
        }
        insertHistory(query);
        displayUpdateStatus(updateStatus);
        parentComponent.dispose();
        parentComponent.makeParentVisible();
        availableDorms.clear();
        groupOfStudents.clear();
    }
    public void changeAllStudents(String toBuildingNo,String fromBuildingNo){
        SwingWorker<Boolean,Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                boolean updateStatus1;
                totalStudents = getTotalStudents(fromBuildingNo);
                do{
                    Thread.sleep(50);
                    loadAvailableDorms(toBuildingNo);
                    groupStudents(fromBuildingNo);
                    updateStatus1 = changeStudents(fromBuildingNo,toBuildingNo);
                    incrementPageNumber();
                    if(getPageNumber()>getTotalPage()) resetPageNumber();
                    updateDormInfo();
                } while (remainingStudents>0);
                return updateStatus1;
            }
            @Override
            protected void process(List<Integer> chunks) {
                JLabel label = parentComponent.getLoadingL();
                label.setVisible(true);
                incrementDots(label);

                int remainingStudents = chunks.get(chunks.size()-1);
                JProgressBar tmp = parentComponent.getLoadingProgressBar();

                tmp.setMinimum(0);
                tmp.setMaximum(totalStudents);
                tmp.setVisible(true);
                tmp.setValue(totalStudents - remainingStudents);
            }
            @Override
            protected void done() {
                boolean updateStatus1;
                try {
                    updateStatus1 = get();
                    parentComponent.getLoadingProgressBar().setVisible(false);
                    parentComponent.getLoadingL().setVisible(false);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
                displayUpdateStatus(updateStatus1);
            }
        };

        worker.execute();
    }
    public void loadAvailableDorms(String buildingNumber){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM AvailableDorm WHERE BuildingNumber='"+buildingNumber+
                "' ORDER BY NumberOfStudents ASC OFFSET "+(getPageNumber()-1)*ROW_PER_PAGE+
                " ROWS FETCH NEXT "+ROW_PER_PAGE+" ROWS ONLY";
        ResultSet resultSet = javaConnection.selectQuery(query);
        System.out.println("Query: "+query);

        try{
            availableDorms.clear();//Erasing previously loaded dorms.
            while(resultSet.next()){
                String roomNo = resultSet.getString("roomNumber");
                int maxCapacity = resultSet.getInt("maxCapacity");
                Dormitory tmpDorm = new Dormitory(roomNo,buildingNumber,maxCapacity);
                tmpDorm.setNoOfStudents(resultSet.getInt("NumberOfStudents"));
                tmpDorm.setNoOfLockers(resultSet.getInt("Lockers"));
                tmpDorm.setDormType(resultSet.getString("DormType"));
                availableDorms.add(tmpDorm);
            }
        } catch (SQLException ex){
            ex.printStackTrace();//For debugging only.
        }
    }

    public int getDormMaxCapacity(String buildingNumber, String roomNumber ){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT maxCapacity FROM DORM WHERE RoomNumber='"+roomNumber+
                "' AND BuildingNumber='"+buildingNumber+"'" ;
        int maxCapacity = 0;
        if(javaConnection.isConnected()){
            ResultSet rs = javaConnection.selectQuery(query);
            try{
                while(rs.next()){
                    maxCapacity = rs.getInt("maxCapacity");
                }
            }catch (SQLException ex){
                ex.printStackTrace(); //For debugging only.
            }
        }
        return maxCapacity;
    }

    public int getDormStudentsNo(Student student){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "";
        int numberOfStudents = 0;
        try{
            query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT " +
                    "WHERE BuildingNumber='"+student.getBuildingNo()+"' AND RoomNumber='"+student.getDormNo()+"'";
        } catch (NullPointerException ex){
            ex.printStackTrace();//For debugging only.
            JOptionPane.showMessageDialog(parentComponent,"Make sure you enter the student id"
                    ,"Invalid input",JOptionPane.ERROR_MESSAGE);
            return numberOfStudents;//To exit the action performed method since it can't continue without dormNo.
        }

        if(javaConnection.isConnected()){
            ResultSet rs = javaConnection.selectQuery(query);
            try{
                while(rs.next()){
                    numberOfStudents = rs.getInt("numberOfStudents");
                }
            } catch (SQLException ex){
                ex.printStackTrace(); //For debugging only.
            }
        }
        return numberOfStudents;
    }

    public int getTotalStudents(String fromBuildingNo){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        int totalStudents = 0;
        String query = "SELECT COUNT(SID) AS TotalStudents FROM STUDENT" +
                " WHERE BuildingNumber='"+fromBuildingNo+"' AND year="+parentComponent.getYear();
        ResultSet resultSet = javaConnection.selectQuery(query);
        try{
            while(resultSet.next()){
                totalStudents = resultSet.getInt("TotalStudents");
            }
        } catch (SQLException ex){
            ex.printStackTrace(); //For debugging only.
        }
        return totalStudents;
    }

    public boolean changeStudent(String sid, String toBuildingNo, String toRoomNo){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "UPDATE STUDENT SET BuildingNumber='"+toBuildingNo+"', " +
                "RoomNumber='"+toRoomNo+"' WHERE SID ='"+sid+"';";
        String historyQuery = "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Change Dorm', '"+
                Request.getCurrentDate()+"' , '"+parentComponent.getBuildingNo()+"')";
        boolean updateStatus = false;

        if(javaConnection.isConnected()){
            updateStatus = javaConnection.updateQuery(query);
            insertHistory(historyQuery);
            historyQuery = "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                    " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Change Dorm', '"+
                    Request.getCurrentDate()+"' , '"+parentComponent.getDestinationBuildingNo()+"')";
            insertHistory(historyQuery);
        }
        return  updateStatus;
    }

    public boolean changeStudents(String fromBuildingNo, String toBuildingNO){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "";

        for(int i = 0; i<availableDorms.size();i++){
            Dormitory tmp = availableDorms.get(i);
            try{
                for(int j = 0; j<groupOfStudents.get(i).size();j++){
                    Student st = groupOfStudents.get(i).get(j);
                    if(tmp.getNoOfStudents()<tmp.getMaxCapacity()){
                        st.setBuildingNo(toBuildingNO);
                        st.setDormNo(tmp.getRoomNO());
                        tmp.setNoOfStudents(tmp.getNoOfStudents()+1); //Increment number of student in every addition.
                    }
                    else{
                        try{
                            groupOfStudents.get(i+1).add(st); //Adds the student to the next group of students.
                        } catch (IndexOutOfBoundsException ex){
                            //No need to implement this method.
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ex){
                //No need to implement this method.
            }

        }
        boolean updateStatus = false;
        for(ArrayList<Student> students: groupOfStudents){
            for(Student student: students){
                query = "UPDATE STUDENT SET BuildingNumber='"+student.getBuildingNo()+
                        "', RoomNumber='"+student.getDormNo()+
                        "' WHERE SID='"+student.getsId()+"';";
                System.out.println("Query: "+query);//Remove after debugging.
                updateStatus = javaConnection.updateQuery(query);
                students.remove(student);
                remainingStudents--;
            }
        }
        boolean allStudentsChanged = (remainingStudents == 0);
        return updateStatus & allStudentsChanged;// Returns false if all students are not changed.
    }

    public void groupStudents(String fromBuildingNo){
        /*
        This method groups students that are
        in the same dorm.
        */
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM STUDENT WHERE BuildingNumber='"+fromBuildingNo+
                "' AND Year="+parentComponent.getYear()+" ORDER BY(RoomNumber) OFFSET "
                +(getPageNumber()-1)*ROW_PER_PAGE+" ROWS FETCH NEXT "+ROW_PER_PAGE+" ROWS ONLY";
        ResultSet resultSet = javaConnection.selectQuery(query);
        ArrayList<Student> tmp = new ArrayList<>();
        TreeSet<String> dormNumbers = new TreeSet<>();
        try{
            while(resultSet.next()){
                Student st = new Student(
                        resultSet.getString("Fname"),resultSet.getString("Lname"),
                        resultSet.getString("SID"),resultSet.getString("Gender"));
                st.setBuildingNo(resultSet.getString("BuildingNumber"));
                String dormNo = resultSet.getString("RoomNumber");
                st.setDormNo(dormNo);
                dormNumbers.add(dormNo);
                tmp.add(st);
            }
        } catch (SQLException ex){
            ex.printStackTrace(); //For debugging only.
        }
        //The following for loop will group the students based on dorm numbers.
        for(String dormNumber: dormNumbers){
            ArrayList<Student> sameDorm = new ArrayList<>();
            for(Student st: tmp){
                if(String.valueOf(st.getDormNo()).equals(dormNumber)){
                    sameDorm.add(st);
                }
            }
            groupOfStudents.add(sameDorm);
        }
    }
    public void sortDormOnBuildingNo(){
        Dormitory tmp;
        for(int i = 0; i<availableDorms.size(); i++){
            for(int j = 0; j<availableDorms.size(); j++){
                boolean isFoundInProctorsBuilding = availableDorms.get(j).getBuildingNo()
                        .equals(parentComponent.getProctor().getBuildingNo());
                if(isFoundInProctorsBuilding){
                    /*
                    The following code will sort the dorms by giving priority to the
                    dorms that are found in the proctor's building.
                    */
                    System.out.println("IsFoundInProctorsBuilding");
                    tmp = availableDorms.get(j);
                    availableDorms.set(j,availableDorms.get(0));
                    availableDorms.set(0,tmp);
                }
            }
        }

        for(int i = 0; i<availableDorms.size(); i++){
            for(int j = 0; j<availableDorms.size(); j++){
                boolean isFoundInDestinationBuilding = availableDorms.get(j).getBuildingNo()
                        .equals(parentComponent.getDestinationBuildingNo());
                if(isFoundInDestinationBuilding){
                    /*
                    The following code will sort the dorms by giving priority to the
                    dorms that are found in the destination building.
                    */
                    tmp = availableDorms.get(j);
                    availableDorms.set(j,availableDorms.get(0));
                    availableDorms.set(0,tmp);
                }
            }
        }
    }

    public int getAvailableSpaceOnBuilding(){
        int totalSpace = 0;
        for(Dormitory dormitory: availableDorms){
            boolean isInDestinationBuilding =
                    dormitory.getBuildingNo().equals(parentComponent.getDestinationBuildingNo());
            if(isInDestinationBuilding)
                totalSpace += dormitory.getMaxCapacity() - dormitory.getNoOfStudents();
        }
        return totalSpace;
    }

    public void displayUpdateStatus(boolean updateStatus){
        if(updateStatus)
            JOptionPane.showMessageDialog(parentComponent,"Change Successful.");
        else
            JOptionPane.showMessageDialog(parentComponent,"Couldn't change all students due to some problem.\n " +
                    "Make sure there is available space and also the destination exits.");
    }

    public void insertHistory(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);

        if(javaConnection.isConnected()){
            javaConnection.insertQuery(query);
        }
    }

}
