package com.smartx.cookies.smartx;

/**
 * Created by zamzamy on 3/18/15.
 */
public class Device {
    int userID;
    String type;
    String name;
    String brand;
    int roomID;

    public Device(){

    }

    public Device(String type, String name, String brand, int roomID) {
        this.type = type;
        this.name = name;
        this.brand = brand;
        this.roomID = roomID;
    }
}
