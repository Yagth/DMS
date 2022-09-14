package BasicClasses.Persons;

public class Student extends Person{
    private String sId;
    private String department;
    private int year;
    private int dormNo;
    private int buildingNo;
    private boolean eligibility;
    private boolean hasAllEquipments;

    public Student(String fName, String lName, String id, String gender){
        super(fName,lName,gender);
        this.sId = id;
        this.department = "";
    }

    public String getsId() {
        return sId;
    }

    public String getDepartment() {
        return department;
    }

    public int getYear() {
        return year;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public boolean isEligibility() {
        return eligibility;
    }
    public int getBuildingNo() {
        return buildingNo;
    }

    public int getDormNo() {
        return dormNo;
    }
    public void setEligibility(boolean eligibility) {
        this.eligibility = eligibility;
    }
    public boolean getEligibility(){
        return this.eligibility;
    }
    public void setBuildingNo(String buildingNo) {
        try{
            this.buildingNo = Integer.parseInt(buildingNo);
        } catch (NumberFormatException ex) {
            //DO nothing here to keep the variable null.
        }
    }
    public void setDormNo(String dormNo) {
        try{
            this.dormNo = Integer.parseInt(dormNo);
        } catch (NumberFormatException ex) {
            //DO nothing here to keep the variable null.
        }
    }

    public void setHasAllEquipments(boolean hasAllEquipments) {
        this.hasAllEquipments = hasAllEquipments;
    }
    public boolean getHasAllEquipments(){
        return hasAllEquipments;
    }

    @Override
    public String toString(){
        return this.getFullName();
    }
}
