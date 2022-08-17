package BasicClasses.Persons;

public class Proctor extends Person{
    private String pId;
    public Proctor(String fName, String lName, String gender){
        super(fName,lName,gender);
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
