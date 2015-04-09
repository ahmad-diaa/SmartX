package models;

/**
 * Created by zamzamy on 3/18/15.
 */
public class Device {
    int userID;
    String type;
    String name;
    String brand;
    String deviceId;
    String status;
    int roomId;
    public Device(int userID, String type, String name, String brand, String deviceId, String status, int roomID) {
        this.userID = userID;
        this.type = type;
        this.name = name;
        this.brand = brand;
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



    public Device(){

    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getRoomID() {
        return roomId;
    }

    public void setRoomID(int roomID) {
        this.roomId = roomID;
    }


}
