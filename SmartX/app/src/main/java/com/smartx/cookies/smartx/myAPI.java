package com.smartx.cookies.smartx;

import java.util.List;

import models.Device;
import models.Note;
import models.Room;
import models.Session;
import models.Type;
import models.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface myAPI {

    @GET("/v/users/{userID}/rooms/{roomName}")
    void findRoom(@Path("userID") String id,@Path("roomName") String name, Callback<List<Room>> callback);

    @FormUrlEncoded
    @POST("/session")
    void login(@Field("session[name]") String username, @Field("session[password]") String password, Callback<Session> callback);

    @GET("/users/{id}")
    public void getFeed(@Path("id") int id, Callback<User> callback);

    @GET("/users/{userID}/rooms/{id}")
    void getRoom(@Path("userID") String userID, @Path("id") String roomID, Callback<String> callback);


    @FormUrlEncoded
    @PUT("/users/{userID}/")
    void changeInfo(@Path("userID") String id, @Field("user[email]") String email, @Field("user[password]") String password, @Field("user[phone]") String phone, Callback<User> callback);

    @FormUrlEncoded
    @POST("/users/{userID}/rooms/")
    void addRoom(@Path("userID") String id, @Field("room[name]") String name ,@Field("room[photo]") String photo ,Callback<Room> callback);
    @FormUrlEncoded
    @PUT("/users/{userID}/rooms/{id}")
    void renameRoom(@Path("userID") String id,@Path("id") String roomID, @Field("room[name]") String name , Callback<Room> callback);

    @GET("/users/{userID}/rooms/{roomID}/devices")
    void viewDevices(@Path("userID") String id, @Path("roomID") String rid, Callback<List<Device>> callback);

    @GET("/types")
    void requestTypes(Callback<List<Type>> types);

    @FormUrlEncoded
    @POST("/users/{user_id}/rooms/{room_id}/devices")
    void addDevice(@Path("user_id") String userid, @Path("room_id") String roomid, @Field("device[name]") String name,@Field("device[device_id]") String device_id, @Field("device[status]") String status, Callback<Device> callback);

    @FormUrlEncoded
    @POST("/users/{user_id}/rooms/{room_id}/devices/{device_device_id}/notes")
    void addNote(@Path("user_id") String userid, @Path("room_id") String roomid,
                 @Path("device_device_id") String deviceID,@Field("note[body]") String noteText, Callback<Note> callback);

    @GET("/users/{user_id}/rooms/{room_id}/devices/{device_device_id}/notes")
    void getNotes(@Path("user_id") String userid, @Path("room_id") String roomid, @Path("device_device_id") String deviceID,
                  Callback<List<Note>> callback);

    @GET("/users/{userID}/rooms/")
    void viewRooms(@Path("userID") String id, Callback<List<Room>> callback);

    @GET("/users/{userID}/")
    void getUser(@Path("userID") String id, Callback<User>callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/")
    void changePassword(@Path("userID") String id, @Field("user[password]") String password, Callback<User> callback);
    @GET("/v/users/{userID}/rooms/{roomID}/devices/{deviceName}")
    void findDevice(@Path("userID") String userID,@Path("roomID") String roomID,@Path("deviceName") String name, Callback<List<Device>> callback);

}

