package models;

/**
 * Created by zamzamy on 3/18/15.
 */
public class Device {
    int userID;

    String name;
    String deviceId;
    String status;
    int roomId;

    public Device(int userID ,String name, String deviceId, String status, int roomID) {
        this.userID = userID;
        this.name = name;
        this.deviceId = deviceId;
        this.status = status;
        this.roomId = roomID;
    }

    public void setDeviceId(String deviceId) {

        this.deviceId = deviceId;
    }
    public String getDeviceId() {

        return deviceId;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {

        return status;
    }
    public Device() {

    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomID() {
        return roomId;
    }

    public void setRoomID(int roomID) {
        this.roomId = roomID;
    }


}
