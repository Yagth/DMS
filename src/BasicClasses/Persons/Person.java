package BasicClasses.Persons;

public abstract class Person {
    private String fName;
    private String lName;
    private String placeOfOrigin;
    private char gender;
    private String phoneNumber;
    Person(String fName, String lName, char gender){
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getFullName(){
        return this.getfName()+" "+this.getlName();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public char getGender() {
        return gender;
    }
    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
