package BasicClasses.Persons;

public class Proctor extends Person{
    private String pId;
    Proctor(String fName, String lName, char gender){
        super(fName,lName,gender);
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
