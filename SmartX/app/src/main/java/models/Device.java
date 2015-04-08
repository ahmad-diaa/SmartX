package models;

public class Device {
    int user_id;
    String type;
    String name;
    String brand;
    String room_id;
    String status;
    String device_id;

    public Device(){

    }

    public Device(String device_id, String name, String roomID, int userID) {
        this.name = name;
        this.room_id = roomID;
        this.user_id=userID;
        this.device_id = device_id;
    }

    public int getUserID() {
        return user_id;
    }

    public void setUserID(int userID) {
        this.user_id = userID;
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

    public String getRoomID() {
        return room_id;
    }

    public void setRoomID(String roomID) {
        this.room_id = roomID;
    }


}
