package com.smartx.cookies.smartx;

public class User {
    private int ID;
    private String name;
    private String password;
    public User(String name,String password) {
        this.name=name;
        this.password=password;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
