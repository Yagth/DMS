package BasicClasses.Rooms;

public class Stock extends Room{
    private int noOfMattress;
    private int noOfPillow;
    private int noOFMattressBase;

    public Stock(String roomNo, String buildinNO){
        super("StockView",roomNo,buildinNO);
    }

    public int getNoOfMattress() {
        return noOfMattress;
    }

    public int getNoOfPillow() {
        return noOfPillow;
    }

    public int getNoOFMattressBase() {
        return noOFMattressBase;
    }

    public void setNoOfMattress(int noOfMattress) {
        this.noOfMattress = noOfMattress;
    }

    public void setNoOfPillow(int noOfPillow) {
        this.noOfPillow = noOfPillow;
    }

    public void setNoOFMattressBase(int noOFMattressBase) {
        this.noOFMattressBase = noOFMattressBase;
    }
}
