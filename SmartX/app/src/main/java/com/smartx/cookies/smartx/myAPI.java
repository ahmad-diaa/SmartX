package com.smartx.cookies.smartx;


import java.util.List;

import models.Device;
import models.Room;
import models.Type;
import models.User;
import retrofit.Callback;
import retrofit.http.*;


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
    @POST("/users/{userId}/rooms/")
    void addRoom(@Path("userId") String id, @Field("room[name]") String name, @Field("room[photo]") String photo, Callback<Room> callback);

    @GET("/users/{userId}/rooms/{roomId}/devices")
    void viewDevices(@Path("userId") String user_id, @Path("roomId") String room_id, Callback<List<Device>> callback);

    @GET("/users/{userId}/rooms/{roomId}/devices/{deviceId}")
    void viewDevice(@Path("userId") String user_id, @Path("roomId") String room_id, @Path("deviceId") String device_id, Callback<Device> callback);

    @FormUrlEncoded
    @PUT("/users/{userId}/rooms/{roomId}/devices/{deviceId}/clickers/{clickerId}")
    void send_clicker_command(@Path("userId") String user_id, @Path("roomId") String room_id, @Path("deviceId") String device_id, @Path("clickerId") String Clicker_id, @Field("clicker[command]") String command, Callback<Clicker> callback);

    @GET("/types/{name}")
    void requestBrands(@Path("name") String device, Callback<List<Type>> types);

    @FormUrlEncoded
    @POST("/users/{userId}/rooms/{roomId}/devices/{deviceId}/clickers")
    void addClicker(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String deviceId, @Field("clicker[command]") String command, Callback<Clicker> callback);
    @FormUrlEncoded

    @GET("/users/{userId}/rooms/{roomId}/devices/{deviceId}/clickers")
    void getClicker(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String device_id, Callback<Clicker> callback);

    @FormUrlEncoded
    @PUT("/users/{userId}/rooms/{roomId}/devices/{deviceId}")
    void editDeviceStatus(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String device_id, @Field("device[status]") String status, Callback<Clicker> callback);

    @FormUrlEncoded
    @POST("/users/{userId}/rooms/{roomId}/devices")
    void addDevice(@Path("userId") String userId, @Path("roomId") String roomId, @Field("device[name]") String name, @Field("device[status]") String status, Callback<Device> callback);

    @GET("/users/{userId}/rooms/")
    void viewRooms(@Path("userId") String id, Callback<List<Room>> callback);

    @FormUrlEncoded
    @PUT("/users/{userId}/")
    void changePassword(@Path("userId") String id, @Field("user[password]") String password, Callback<User> callback);

    @FormUrlEncoded
    @GET("/users/{userId}/")
    void getUser(@Path("userId") String id, Callback<User> callback);


}
