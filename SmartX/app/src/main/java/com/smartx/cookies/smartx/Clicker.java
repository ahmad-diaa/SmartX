package com.smartx.cookies.smartx;

/**
 * Created by youmna on 4/3/15.
 */
public class Clicker {
    int userId;//current userId
    int roomId;//Current roomId
    int deviceId;//current deviceId
    int clickerId;//current clickerId
    String command;//recently sent command

    public Clicker() {
    }

    public Clicker(int userId, int roomId, int deviceId, int clickerId, String command) {
        this.userId = userId;
        this.roomId = roomId;
        this.deviceId = deviceId;
        this.clickerId = clickerId;
        this.command = command;
    }

    /*
    ClickerId setter
    @params new clickerID
     */
    public void setClickerId(int clickerId) {

        this.clickerId = clickerId;
    }

    /*
    ClickerId getter
    @return clickerID
     */
    public int getClickerId() {

        return clickerId;
    }

    /*
userId setter
@params new userID
 */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /*
    roomId setter
    @params new roomID
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /*
     userId getter
     @return userID
      */
    public int getUserId() {

        return userId;
    }

    /*
     roomId getter
     @return roomID
      */
    public int getRoomId() {
        return roomId;
    }

       /*
       deviceId setter
       @params deviceID
        */
    public void setDeviceId(int device_id) {
        this.deviceId = device_id;
    }

       /*
       command setter
       @params command
        */
    public void setCommand(String command) {
        this.command = command;
    }

       /*
       deviceId getter
       @return deviceID
        */
    public int getDeviceId() {
        return deviceId;
    }

      /*
       command getter
       @return command
        */
    public String getCommand() {
        return command;
    }
}
