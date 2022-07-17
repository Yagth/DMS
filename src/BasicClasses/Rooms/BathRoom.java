package BasicClasses.Rooms;

public class BathRoom extends Room{
    private int noOfWorkingShowers;
    private int noOfToilets;

    public BathRoom(String roomNo, String buildingNo){
        super("BathRoom",roomNo,buildingNo);
    }

    public int getNoOfWorkingShowers() {
        return noOfWorkingShowers;
    }

    public int getNoOfToilets() {
        return noOfToilets;
    }

    public void setNoOfWorkingShowers(int noOfWorkingShowers) {
        this.noOfWorkingShowers = noOfWorkingShowers;
    }

    public void setNoOfToilets(int noOfToilets) {
        this.noOfToilets = noOfToilets;
    }
}
