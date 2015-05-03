package models;
/**
 * SE Sprint1
 * Device.java
 *
 * @author Amir
 */
public class Device {
    /**
     * The id of the user having the device.
     */
    int user_id;
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
    int room_id;
    /**
     * The primary key of device.
     */
    String device_id;
    /**
     * true if device exits in favorites list, false otherwise.
     */
    String favorite;
    /**
     * Constructor to create a device with given parameters.
     *
     * @param name the type of the device.
     * @param roomID the id of the room to which the device belongs.
     * @param userID the id of the user having the device.
     * @param deviceID the primary key of device.
     * @param status the status of the device: on or off.
     */
    public Device(String name, int roomID, int userID, String deviceID, String status) {
        this.name = name;
        this.room_id = roomID;
        this.user_id = userID;
        this.status = status;
        this.device_id = deviceID;
        this.favorite="false";
    }
    /**
     * get the id of the user having the device.
     *
     * @return the id of the user.
     */
    public int getUserID() {
        return user_id;
    }
    /**
     * set the id of the user having the device.
     *
     * @param userID the id of the user.
     */
    public void setUserID(int userID) {
        this.user_id = userID;
    }
    /**
     * get the primary key of device.
     *
     * @return id of the device.
     */
    public String getDeviceID() {
        return device_id;
    }
    /**
     * set get the primary key of device.
     *
     * @param deviceID the id of device.
     */
    public void setDeviceID(String deviceID) {
        this.device_id = deviceID;
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
        return room_id;
    }
    /**
     * set the id of the room to which the device belongs.
     *
     * @param roomID the id of the room.
     */
    public void setRoomID(int roomID) {
        this.room_id = roomID;
    }
    /**
     * get the value that shows if the device exits in favorites list or not.
     *
     * @return favorite flag.
     */
    public String getFavorite() {
        return favorite;
    }
    /**
     * set the value that shows if the device exits in favorites list or not.
     *
     * @param favorite
     */
    public void setFavorite(String favorite) {
        this.favorite=favorite;
    }
    public String getId() {
        return device_id;
    }
}