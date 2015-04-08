package models;

public class Device {
    int userID;
    String type;
    String name;
    String brand;
    int roomID;
    int id;

    public Device(){

    }
    public Device(String type, String name, String brand, int roomID, int userID) {
        this.type = type;
        this.name = name;
        this.brand = brand;
        this.roomID = roomID;
        this.userID=userID;

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

    public int getId() {
        return id;
    }

}