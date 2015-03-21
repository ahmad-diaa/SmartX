package com.smartx.cookies.smartx;

/**
 * Created by youmna on 3/18/15.
 */
public class Session {
    private String access_token;
    private int user_id;

    public Session() {

    }

    public String getToken() {
        return access_token;
    }

    public void setToken(String token) {
        this.access_token = token;
    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }
}