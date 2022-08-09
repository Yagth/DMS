package BasicClasses.Requests;

import BasicClasses.Others.Cloth;

import java.util.ArrayList;

public class ClothTakeOutRequest extends Request {
    private ArrayList<Cloth> cloths;
    private static int requestCount;
    private int requestId;
    public ClothTakeOutRequest(String requesterId,Integer requestCount){
        super("ClothTakeOutForm",requesterId);
        setRequestCount(requestCount);
        cloths = new ArrayList<>();
        requestCount++;
        this.requestId = requestCount;
    }
    public ClothTakeOutRequest(Cloth cloth, String requesterId,Integer requestCount){
        this(requesterId,requestCount);
        cloths.add(cloth);
    }

    public ArrayList<Cloth> getClothsList() {
        return cloths;
    }

    public void addCloth(Cloth cloth){
        cloths.add(cloth);
    }
    public void setRequestCount(Integer requestCount){
        if(requestCount.equals(null)){
            requestCount = 0;
        }
        else this.requestCount = requestCount;
    }

    @Override
    public String toString(){
        String clothsAsString = "";
        for(Cloth cloth: cloths){
            clothsAsString += cloth.getClothName() + ", ";
        }
        return clothsAsString;
    }

    @Override
    public int getRequestId() {
        return requestId;
    }
}
