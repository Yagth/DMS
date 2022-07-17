package BasicClasses.Requests;

import java.util.ArrayList;

public class ClothTakeOutForm extends Request {
    private ArrayList<Cloth> cloths;

    public ClothTakeOutForm(Cloth cloth,String requesterId){
        super("ClothTakeOutForm",requesterId);
        cloths = new ArrayList<>();
        cloths.add(cloth);
    }

    public ArrayList<Cloth> getClothsList() {
        return cloths;
    }

    public void addCloth(Cloth cloth){
        cloths.add(cloth);
    }

    @Override
    public String toString(){
        String clothsAsString = "";
        for(Cloth cloth: cloths){
            clothsAsString += cloth.getClothType() + ", ";
        }
        return clothsAsString;
    }

    private class Cloth{
        private String clothType;
        private int amount;

        Cloth(String clothType, int amount){
            this.clothType = clothType;
            this.amount = amount;
        }

        public String getClothType() {
            return clothType;
        }

        public int getAmount() {
            return amount;
        }

    }
}
