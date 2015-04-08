package models;

/**
 *SE Sprint1
 *Device.java
 *
 * @author Amir
 */

public class Device {
    /**
     * The id of the user having the device.
     */
    int userID;

    /**
     * The type of the device.
     */
    String name;

    /**
     * The status of the device: on or off.
     */
    String status;

    /**
     * The id of the room to which the device belongs.
     */
    int roomID;

    /**
     * The primary key of device.
     */
    String deviceID;


    /**
     * Constructor to create a device with given parameters.
     */

    public Device( String name, int roomID, int userID, String deviceID, String status) {
        this.name = name;
        this.roomID = roomID;
        this.userID=userID;
        this.deviceID=deviceID;
        this.status=status;
    }

    /**
     * get the id of the user having the device.
     *
     * @return the id of the user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * set the id of the user having the device.
     *
     * @param userID the id of the user.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * get the primary key of device.
     *
     * @return id of the device.
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * set get the primary key of device.
     *
     * @param deviceID the id of device.
     */
    public void setDeviceID(String deviceID) {
        this.deviceID= deviceID;
    }

    /**
     * get type of the device.
     *
     * @return type of the device.
     */
    public String getName() {
        return name;
    }

    /**
     * set type of the device.
     *
     * @param name the type of the device.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the status of the device: on or off. 
     *
     * @return status of the device.
     */
    public String getStatus() {
        return status;
    }

    /**
     * set the status of the device: on or off. 
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * get the id of the room to which the device belongs.
     *
     * @return id of the room.
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * set the id of the room to which the device belongs.
     *
     * @param roomID the id of the room.
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }


}

