package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Persons.Student;
import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.DomainLoadStoreParameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class ChangeButtonListener implements ActionListener {
    private ChangeDormView parentComponent;
    private ArrayList<Dormitory> availableDorms;
    private ArrayList<ArrayList<Student>> groupOfStudents; //Collection of students that are in the same dorm.
    private JavaConnection javaConnection;
    public ChangeButtonListener(ChangeDormView parentComponent){
        this.parentComponent = parentComponent;
        availableDorms = new ArrayList<>();
        groupOfStudents = new ArrayList<>();
        javaConnection = new JavaConnection(JavaConnection.URL);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String condition = parentComponent.getSelectedCondition();
        String fromBuildingNo = parentComponent.getBuildingNo();
        String fromDormNo = parentComponent.getDormNo();
        String toBuildingNo = parentComponent.getDestinationBuildingNo();
        String toRoomNo = parentComponent.getDestinationRoomNo();
        String query;
        ResultSet resultSet = null;
        if(condition.equals("Change single student")){
            query = "UPDATE STUDENT SET BuildingNumber='"+toBuildingNo+"', " +
                    "RoomNumber='"+toRoomNo+"' " +
                    "WHERE BuildingNumber='"+fromBuildingNo+"' AND RoomNumber='"+fromDormNo+"';";
        }
        else {
            if(javaConnection.isConnected()){
                loadAvailableDorms();
                sortDormOnAvailableSpace();
                sortDormOnBuildingNo();
                groupStudents(fromBuildingNo);
                changeStudents(fromBuildingNo,toBuildingNo);
            }

        }
    }

    public boolean changeStudents(String fromBuildingNo, String toBuildingNO){
        String query = "SELECT COUNT(SID) AS TotalStudents FROM STUDENTS" +
                "WHERE BuildingNo='"+fromBuildingNo+"' AND year="+parentComponent.getYear();
        ResultSet resultSet = javaConnection.selectQuery(query);
        int totalStudents = 0;
        int totalSpace = getTotalAvailableSpace();
        try{
            while(resultSet.next()){
                totalStudents = resultSet.getInt("TotalStudents");
            }
        } catch (SQLException ex){
            ex.printStackTrace(); //For debugging only.
        }
        System.out.println("Total Students: "+totalStudents);//For debugging only.
        System.out.println("Total space: "+totalSpace);//For debugging only.

        for(int i = 0; i<totalSpace;i++){
            Dormitory tmp = availableDorms.get(i);
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
                        ex.printStackTrace();//For debugging only.
                    }
                }
            }
        }
        boolean updateStatus = false;
        for(ArrayList<Student> students: groupOfStudents){
            for(Student student: students){
                query = "UPDATE STUDENT SET BuildingNumber='"+student.getBuildingNo()+
                        "', RoomNumber='"+student.getDormNo()+
                        " WHERE SID='"+student.getsId()+"';";
                updateStatus = javaConnection.updateQuery(query);
            }
        }
        return updateStatus;// Returns false if all students are not changed.
    }

    public void groupStudents(String fromBuildingNo){
        /*
        This method groups students that are
        in the same dorm.
        */
        String query = "SELECT * FROM STUDENT WHERE BuildingNumber='"+fromBuildingNo+"' ORDER BY(BuildingNumber, RoomNumber)";
        ResultSet resultSet = javaConnection.selectQuery(query);
        ArrayList<Student> tmp = new ArrayList<>();
        TreeSet<String> dormNumbers = new TreeSet<>();
        try{
            while(resultSet.next()){
                Student st = new Student(
                        resultSet.getString("Fname"),resultSet.getString("Lname"),
                        resultSet.getString("SID"),resultSet.getString("Gender"));
                st.setBuildingNo(resultSet.getString("BuildingNumber"));
                st.setDormNo(resultSet.getString("RoomNumber"));
                dormNumbers.add(resultSet.getString("RoomNumber"));
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
        String query = "SELECT BuildingNumber, RoomNumber, maxCapacity FROM DORM AS D WHERE (SELECT COUNT(BuildingNumber, RoomNumber) " +
                "FROM STUDENT AS S WHERE D.BuildingNumber=S.BuildingNumber AND " +
                "D.RoomNumber=S.RoomNumber)< maxCapacity;";
        ResultSet resultSet = javaConnection.selectQuery(query);
        try{
            while(resultSet.next()){
                String roomNo = resultSet.getString("roomNumber");
                String buildingNumber = resultSet.getString("buildingNumber");
                int maxCapacity = resultSet.getInt("maxCapacity");
                Dormitory tmp = new Dormitory(buildingNumber,roomNo,maxCapacity);

                String query2 = "SELECT COUNT(SID) AS numberOfStudents FROM STUDENTS " +
                        "WHERE BuildingNumber='"+buildingNumber+"' AND RoomNumber='"+roomNo+"'";
                ResultSet rs = javaConnection.selectQuery(query2);

                while(rs.next()){
                    tmp.setNoOfStudents(rs.getInt("numberOfStudents"));
                }

                availableDorms.add(tmp);
            }
        } catch (SQLException ex){
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
                if(availableDorms.get(j).getNoOfStudents() > availableDorms.get(j+1).getNoOfStudents()){
                    tmp = availableDorms.get(j+1);
                    availableDorms.set(j+1,availableDorms.get(j));
                    availableDorms.set(j,tmp);
                }
            }
        }
    }

    public int getTotalAvailableSpace(){
        int totalSpace = 0;
        for(Dormitory dormitory: availableDorms){
            totalSpace += dormitory.getMaxCapacity() - dormitory.getNoOfStudents();
        }
        return totalSpace;
    }
}
