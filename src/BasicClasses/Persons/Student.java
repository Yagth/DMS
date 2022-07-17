package BasicClasses.Persons;

public class Student extends Person{
    private String sId;
    private String department;
    private int year;
    private int dormNo;
    private int buildingNo;
    private boolean eligblity;

    Student(String fName, String lName, String id, char gender){
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

}
