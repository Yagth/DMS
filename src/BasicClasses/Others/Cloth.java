package BasicClasses.Others;

public class Cloth {
    private String clothName;
    private int clothAmount;

    public Cloth(String clothName, int clothAmount){
        this.clothName = clothName;
        this.clothAmount = clothAmount;
    }
    public Cloth(int clothAmount, String clothName){
        this(clothName,clothAmount);
    }

    public String getClothName() {
        return clothName;
    }

    public int getClothAmount() {
        return clothAmount;
    }

    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public void setClothAmount(int clothAmount) {
        this.clothAmount = clothAmount;
    }
}
