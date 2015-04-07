package models;

/**
 * Created by zamzamy on 3/18/15.
 */
public class Device {
    int userID;
    String type;
    String name;
    String brand;
    String device_id;

    public Device(int userID, String type, String name, String brand, String device_id, String status, int roomID) {
        this.userID = userID;
        this.type = type;
        this.name = name;
        this.brand = brand;
        this.device_id = device_id;
        this.status = status;
        this.roomID = roomID;
    }

    public void setDevice_id(String device_id) {

        this.device_id = device_id;
    }

    public String getDevice_id() {

        return device_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {

        return status;
    }

    String status;
    int roomID;

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
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }


}
