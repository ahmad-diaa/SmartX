package com.smartx.cookies.smartx;

/**
 * Created by youmna on 4/3/15.
 */
public class Clicker {
    String user_id;
    String room_id;
    String device_id;
    String clicker_id;
    String command;


    public Clicker(String user_id, String room_id, String device_id, String clicker_id, String command) {
        this.user_id = user_id;
        this.room_id = room_id;
        this.device_id = device_id;
        this.clicker_id = clicker_id;
        this.command = command;
    }

    public void setClicker_id(String clicker_id) {

        this.clicker_id = clicker_id;
    }

    public String getClicker_id() {

        return clicker_id;
    }

    public Clicker(String uid, String rid, String did, String c){
        user_id = uid;
        room_id = rid;
        device_id = did;
        command = c;

    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getUser_id() {

        return user_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getCommand() {
        return command;
    }
}
