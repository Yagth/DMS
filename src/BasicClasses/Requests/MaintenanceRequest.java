package BasicClasses.Requests;

public class MaintenanceRequest extends Request {
    private String roomNO;
    private String buildingNo;
    public MaintenanceRequest(String requesterId){
        super("Maintenance",requesterId);
    }

    public String getRoomNO() {
        return roomNO;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public void setRoomNO(String roomNO) {
        this.roomNO = roomNO;
    }
}
