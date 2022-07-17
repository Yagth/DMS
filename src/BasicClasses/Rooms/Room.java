package BasicClasses.Rooms;

public abstract class Room {
    private String roomType;
    private String roomNO;
    private String buildingNo;

    public Room(String roomType,String roomNo, String buildingNo){
        this.roomType = roomType;
        this.roomNO = roomNo;
        this.buildingNo = buildingNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public String  getRoomNO() {
        return roomNO;
    }

    public String  getBuildingNo() {
        return buildingNo;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setRoomNO(String roomNO) {
        this.roomNO = roomNO;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }
}
