package BasicClasses.Rooms;

public class Dormitory extends Room{
    private int noOfLockers;
    private int noOfBeds;
    private int noOfChairs;
    private int noOfTables;
    private String keyHolderId;

    Dormitory(String roomNo, String buildingNo){
        super("Dormitory",roomNo,buildingNo);
        keyHolderId="";
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
}
