package BasicClasses.Requests;

public class ExtendDormStayRequest extends Request {
    String roomNumber;
    String buildingNumber;
    public ExtendDormStayRequest(String requesterId){
        super("ExtendDormStayRequest",requesterId);
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }
}
