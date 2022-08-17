package BasicClasses.Persons;

public class Proctor extends Person{
    private String pId;
    private int buildingNo;
    public Proctor(String fName, String lName, String gender){
        super(fName,lName,gender);
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = Integer.parseInt(buildingNo);
    }

    public int getBuildingNo() {
        return buildingNo;
    }
}
