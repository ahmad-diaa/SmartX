package models;

public class Device {
    int userID;
    String name;
    String status;
    int roomID;
    String deviceID;
    public Device(){

    }

    public Device( String name, int roomID, int userID, String deviceID, String status) {
        this.name = name;
        this.roomID = roomID;
        this.userID=userID;
        this.deviceID=deviceID;
        this.status=status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String type) {
        this.deviceID= deviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String name) {
        this.status = status;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }


}
