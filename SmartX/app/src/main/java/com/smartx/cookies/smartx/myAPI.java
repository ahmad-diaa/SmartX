package com.smartx.cookies.smartx;


import java.util.List;

import models.Room;
import models.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by zamzamy on 3/11/15.
 */
public interface myAPI {
    @FormUrlEncoded
    @POST("/session")
    void login(@Field("session[name]") String username, @Field("session[password]") String password,
               Callback<Session> callback);


    @GET("/users/{id}")
    public void getFeed(@Path("id") int id, Callback<User> callback);

    @FormUrlEncoded
    @POST("/users/{userID}/rooms/")
    void addRoom(@Path("userID") String id, @Field("room[name]") String name
            , @Field("room[photo]") String photo, @Field("room[room_id]") String room_id, Callback<Room> callback);


    @GET("/users/{userID}/rooms/")
    void viewRooms(@Path("userID") String id, Callback<List<Room>> callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/")
    void changePassword(@Path("userID") String id, @Field ("user[password]") String password, Callback<User> callback);

    @GET("/users/{userID}/")
    void getUser (@Path("userID") String id, Callback <User> callback);
}
