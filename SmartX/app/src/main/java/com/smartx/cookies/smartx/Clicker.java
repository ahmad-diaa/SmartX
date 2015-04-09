package com.smartx.cookies.smartx;

/**
 * Created by youmna on 4/3/15.
 */
public class Clicker {
    int user_id;
    int room_id;
    int device_id;
    int clicker_id;
    String command;

public Clicker(){}
    public Clicker(int user_id, int room_id, int device_id, int clicker_id, String command) {
        this.user_id = user_id;
        this.room_id = room_id;
        this.device_id = device_id;
        this.clicker_id = clicker_id;
        this.command = command;
    }

    public void setClickerId(int clicker_id) {

        this.clicker_id = clicker_id;
    }

    public int getClickerId() {

        return clicker_id;
    }

    public Clicker(int userID, int roomID, int deviceID, String command) {
        user_id = userID;
        room_id = roomID;
        device_id = deviceID;
        this.command = command;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setRoomId(int room_id) {
        this.room_id = room_id;
    }

    public int getUserId() {

        return user_id;
    }

    public int getRoomId() {
        return room_id;
    }

    public void setDeviceId(int device_id) {
        this.device_id = device_id;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getDeviceId() {
        return device_id;
    }

    public String getCommand() {
        return command;
    }
}
