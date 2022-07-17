package BasicClasses.Rooms;

import java.util.ArrayList;

public class Building {
    private String buildingNo;
    private int noOfDorms;
    private int noOfBathrooms;
    private ArrayList<String> proctorsId;

    public Building(String buildingNo){
        this.buildingNo = buildingNo;
    }

    public int getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public int getNoOfDorms() {
        return noOfDorms;
    }

    public ArrayList<String> getProctorsId() {
        return proctorsId;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public void setNoOfDorms(int noOfDorms) {
        this.noOfDorms = noOfDorms;
    }

    public void setNoOfBathrooms(int noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }

    public void setProctorsId(ArrayList<String> proctorsId) {
        this.proctorsId = proctorsId;
    }
}
