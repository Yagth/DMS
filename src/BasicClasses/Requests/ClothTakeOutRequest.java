package BasicClasses.Requests;

import BasicClasses.Others.Cloth;

import java.util.Vector;

public class ClothTakeOutRequest extends Request {
    private Vector<Cloth> cloths;
    private static int requestCount;
    public ClothTakeOutRequest(String requesterId) {
        super("ClothTakeOutForm", requesterId);
    }
    public ClothTakeOutRequest(String requesterId,Integer requestCount){
        super("ClothTakeOutForm",requesterId);
        requestCount++;
        setRequestCount(requestCount);
        cloths = new Vector<>();
    }
    public ClothTakeOutRequest(Cloth cloth, String requesterId,Integer requestCount){
        this(requesterId,requestCount);
        cloths.add(cloth);
    }

    public Vector<Cloth> getClothsList() {
        return cloths;
    }

    public void addCloth(Cloth cloth){
        cloths.add(cloth);
    }
    public void setRequestCount(Integer requestCount){
        this.requestCount = requestCount;
    }
    public int getRequestCount(){return requestCount;}

    @Override
    public String toString(){
        String clothsAsString = "";
        for(Cloth cloth: cloths){
            clothsAsString += cloth.getClothName() + ", ";
        }
        return clothsAsString;
    }
}
