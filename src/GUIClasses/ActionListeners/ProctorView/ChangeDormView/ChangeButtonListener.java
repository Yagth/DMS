package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Requests.Request;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.ChangeDormView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

public class ChangeButtonListener implements ActionListener {
    private ChangeDormView parentComponent;
    private ArrayList<Dormitory> availableDorms;
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
        String query2 = "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Change Dorm', '"+
                Request.getCurrentDate()+"' , '"+parentComponent.getBuildingNo()+"')";

        if(toBuildingNo.equals("")){
            JOptionPane.showMessageDialog(parentComponent,"Destination building is empty",
                    "Empty message",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(condition.equals("Change single student")){
            try{
                query = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT " +
                        "WHERE BuildingNumber='"+student.getBuildingNo()+"' AND RoomNumber='"+student.getDormNo()+"'";
            } catch (NullPointerException ex){
                ex.printStackTrace();//For debugging only.
                JOptionPane.showMessageDialog(parentComponent,"Make sure you enter the student id"
                        ,"Invalid input",JOptionPane.ERROR_MESSAGE);
                return;//To exit the action performed method since it can't continue without dormNo.
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
            int maxCapacity = 0;
            query = "SELECT maxCapacity FROM DORM WHERE RoomNumber='"+toRoomNo+
                    "' AND BuildingNumber='"+toBuildingNo+"'";
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

            query = "UPDATE STUDENT SET BuildingNumber='"+toBuildingNo+"', " +
                    "RoomNumber='"+toRoomNo+"' " +
                    "WHERE SID ='"+student.getsId()+"';";
            if(javaConnection.isConnected()){
                updateStatus = javaConnection.updateQuery(query);
                insertHistory(query2);
            }
        }
        else {
            if(fromBuildingNo.equals("")){
                JOptionPane.showMessageDialog(parentComponent,"From building is empty",
                        "Empty message",JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(javaConnection.isConnected()){
                loadAvailableDorms();

                sortDormOnAvailableSpace();
                System.out.println("--Available dorms after sort on space--");
                displayAvailableDorms();

                sortDormOnBuildingNo();
                System.out.println("--Available dorms after sort on buildingNumber--");
                displayAvailableDorms();

                groupStudents(fromBuildingNo);
                updateStatus = changeStudents(fromBuildingNo,toBuildingNo);
                insertHistory(query2);
            }
        }
        displayUpdateStatus(updateStatus);
        parentComponent.dispose();
        parentComponent.makeParentVisible();
        availableDorms.clear();
        groupOfStudents.clear();
    }

    public boolean changeStudents(String fromBuildingNo, String toBuildingNO){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT COUNT(SID) AS TotalStudents FROM STUDENT" +
                " WHERE BuildingNumber='"+fromBuildingNo+"' AND year="+parentComponent.getYear();
        ResultSet resultSet = javaConnection.selectQuery(query);
        int totalStudents = 0;
        int totalSpace = getAvailableSpaceOnBuilding();
        try{
            while(resultSet.next()){
                totalStudents = resultSet.getInt("TotalStudents");
            }
        } catch (SQLException ex){
            ex.printStackTrace(); //For debugging only.
        }
        System.out.println("Total Students: "+totalStudents);//For debugging only.
        System.out.println("Total space: "+totalSpace);//For debugging only.

        for(int i = 0; i<availableDorms.size();i++){
            Dormitory tmp = availableDorms.get(i);
            try{
                for(int j = 0; j<groupOfStudents.get(i).size();j++){
                    Student st = groupOfStudents.get(i).get(j);
                    boolean isValidDorm = tmp.getNoOfStudents()<tmp.getMaxCapacity() & tmp.getDormType().equals(st.getGender());
                    if(isValidDorm){
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
            }
        }
        boolean allStudentsChanged = totalSpace>=totalStudents;
        return updateStatus & allStudentsChanged;// Returns false if all students are not changed.
    }

    public void groupStudents(String fromBuildingNo){
        /*
        This method groups students that are
        in the same dorm.
        */
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM STUDENT WHERE BuildingNumber='"+fromBuildingNo+
                "' AND Year="+parentComponent.getYear()+" ORDER BY(RoomNumber)";
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
    public void loadAvailableDorms(){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT * FROM AvailableDorm";
        ResultSet resultSet = javaConnection.selectQuery(query);
        try{
            while(resultSet.next()){
                JavaConnection javaConnection1 = new JavaConnection(JavaConnection.URL);
                String roomNo = resultSet.getString("roomNumber");
                String buildingNumber = resultSet.getString("buildingNumber");
                int maxCapacity = resultSet.getInt("maxCapacity");
                Dormitory tmp = new Dormitory(roomNo,buildingNumber,maxCapacity);

                String query2 = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENT " +
                        "WHERE BuildingNumber='"+buildingNumber+"' AND RoomNumber='"+roomNo+"'";
                ResultSet rs = javaConnection1.selectQuery(query2);

                while(rs.next()){
                    tmp.setNoOfStudents(rs.getInt("numberOfStudents"));
                }

                availableDorms.add(tmp);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            //Leave the implementation of this block.
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

    public void sortDormOnAvailableSpace(){
        for(int i = 0; i<availableDorms.size(); i++){
            for(int j = 0; j<availableDorms.size(); j++){
                Dormitory tmp;
                try{
                    int availableSpace1 = availableDorms.get(j).getMaxCapacity()-availableDorms.get(j).getNoOfStudents();
                    int availableSpace2 = availableDorms.get(j+1).getMaxCapacity()-availableDorms.get(j+1).getNoOfStudents();
                    if( availableSpace1 < availableSpace2 ){
                        tmp = availableDorms.get(j+1);
                        availableDorms.set(j+1,availableDorms.get(j));
                        availableDorms.set(j,tmp);
                    }
                } catch (IndexOutOfBoundsException ex){
                    //No need to implement this block.
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

    //The following method is only for debugging.
    private void displayAvailableDorms(){
        for(Dormitory dorm: availableDorms){
            System.out.println("BuildingNumber: "+dorm.getBuildingNo());
            System.out.println("Available Space: "+(dorm.getMaxCapacity()-dorm.getNoOfStudents()));
        }
    }
}
