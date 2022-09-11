package BasicClasses.Rooms;

public class Dormitory extends Room{
    private int noOfLockers;
    private int noOfBeds;
    private int noOfChairs;
    private int noOfTables;
    private int maxCapacity;
    private int noOfStudents;
    private String keyHolderId;
    private String dormType;

    public Dormitory(String roomNo, String buildingNo){
        super("Dormitory",roomNo,buildingNo);
        keyHolderId="";
    }
    public Dormitory(String roomNo, String buildingNo,int maxCapacity){
        this(roomNo,buildingNo);
        this.maxCapacity = maxCapacity;
    }

    public int getNoOfLockers() {
        return noOfLockers;
    }

    public int getNoOfBeds() {
        return noOfBeds;
    }

    public int getNoOfChairs() {
        return noOfChairs;
    }

    public int getNoOfTables() {
        return noOfTables;
    }

    public String getKeyHolderId() {
        return keyHolderId;
    }

    public void setNoOfLockers(int noOfLockers) {
        this.noOfLockers = noOfLockers;
    }

    public void setNoOfBeds(int noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public void setNoOfChairs(int noOfChairs) {
        this.noOfChairs = noOfChairs;
    }

    public void setNoOfTables(int noOfTables) {
        this.noOfTables = noOfTables;
    }

    public void setKeyHolderId(String keyHolderId) {
        this.keyHolderId = keyHolderId;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }

    public String getDormType() {
        return dormType;
    }

    public void setDormType(String dormType) {
        this.dormType = dormType;
    }
}
