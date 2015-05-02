package com.smartx.cookies.smartx;

import java.util.List;

import models.Device;
import models.Note;
import models.Room;
import models.Session;
import models.Type;
import models.User;
import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * SE Sprint2
 * myAPI.java
 * Purpose: api interface to interact with rails.
 *
 * @author Amir
 */

public interface myAPI {

    @GET("/v/users/{userID}/rooms/{roomName}")
    void findRoom(@Path("userID") String id, @Path("roomName") String name, Callback<List<Room>> callback);

    @GET("/users/{userID}/rooms/1/devices/1/devices")
    void allDevices(@Path("userID") String id, Callback<List<Device>> callback);

    /**
     * It takes the user's access token and destroys user's session so that all of his operations will be disabled.
     *
     * @param access_token
     */
    @DELETE("/session/{token}")
    void logout(@Path("token") String access_token, Callback<Session> callback);

    @GET("/v/types/{name}")
    void findClickerType(@Path("name") String deviceName, Callback<List<Type>> callback);

    @FormUrlEncoded
    @POST("/session")
    void login(@Field("session[name]") String username, @Field("session[password]") String password, Callback<Session> callback);

    @GET("/users/{id}")
    public void getFeed(@Path("id") int id, Callback<User> callback);

    @GET("/users/{userID}/rooms/{id}")
    void getRoom(@Path("userID") String userID, @Path("id") String roomID, Callback<String> callback);

    @GET("/users/{userID}/rooms/{id}")
    void getRoom2(@Path("userID") String userID, @Path("id") String roomID, Callback<Room> callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/")
    void changeInfo(@Path("userID") String id, @Field("user[email]") String email, @Field("user[password]") String password, @Field("user[phone]") String phone, Callback<User> callback);

    @FormUrlEncoded
    @PUT("/users/{userId}/rooms/{roomId}/devices/{deviceId}/clickers/{clickerId}/")
    void sendClickerCommand(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String deviceId, @Path("clickerId") String ClickerId, @Field("clicker[command]") String command, Callback<Clicker> callback);

    @FormUrlEncoded
    @POST("/users/{userId}/rooms/{roomId}/devices/{deviceId}/clickers/")
    void addClicker(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String deviceId, @Field("clicker[command]") String command, Callback<Clicker> callback);

    @GET("/users/{userId}/rooms/{roomId}/devices/{deviceId}/clickers/")
    void getClicker(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String deviceId, Callback<Clicker> callback);

    @FormUrlEncoded
    @POST("/users/{userID}/rooms/")
    void addRoom(@Path("userID") String id, @Field("room[name]") String name, Callback<Room> callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/rooms/{id}")
    void renameRoom(@Path("userID") String id, @Path("id") String roomID, @Field("room[name]") String name, Callback<Room> callback);

    @GET("/users/{userID}/rooms/{roomID}/devices")
    void viewDevices(@Path("userID") String id, @Path("roomID") String rid, Callback<List<Device>> callback);


    @GET("/types")
    void requestTypes(Callback<List<Type>> types);


    @GET("/users/{userId}/rooms/{roomId}/devices/{deviceId}/")
    void getDevice(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String deviceId, Callback<Device> callback);

    @FormUrlEncoded
    @POST("/users/{userId}/rooms/{roomId}/devices/")
    void addDevice(@Path("userId") String userId, @Path("roomId") String roomId, @Field("device[device_id]") String deviceId, @Field("device[name]") String name, @Field("device[status]") String status, Callback<Device> callback);

    @GET("/users/{userId}/rooms/")
    void viewRooms(@Path("userId") String id, Callback<List<Room>> callback);

    @FormUrlEncoded
    @POST("/users/{user_id}/rooms/{room_id}/devices/{device_device_id}/notes")
    void addNote(@Path("user_id") String userid, @Path("room_id") String roomid,
                 @Path("device_device_id") String deviceID, @Field("note[body]") String noteText, Callback<Note> callback);

    @GET("/users/{user_id}/rooms/{room_id}/devices/{device_device_id}/notes")
    void getNotes(@Path("user_id") String userid, @Path("room_id") String roomid, @Path("device_device_id") String deviceID,
                  Callback<List<Note>> callback);

    @FormUrlEncoded
    @PUT("/users/{userId}/rooms/{roomId}/devices/{deviceId}/")
    void editDeviceStatus(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String deviceId, @Field("device[status]") String status, Callback<Device> callback);


    @FormUrlEncoded
    @PUT("/users/{userId}/rooms/{roomId}/devices/{deviceId}/")
    void editDeviceType(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String deviceId, @Field("device[type]") String type, Callback<Device> callback);


    @GET("/users/{userId}/")
    void getUser(@Path("userId") String id, Callback<User> callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/")
    void changePassword(@Path("userID") String id, @Field("user[password]") String password, Callback<User> callback);

    @GET("/v/users/{userID}/rooms/{roomID}/devices/{deviceName}")
    void findDevice(@Path("userID") String userID, @Path("roomID") String roomID, @Path("deviceName") String name, Callback<List<Device>> callback);

    /**
     * It returns the value of favorite attribute of a specific device given its id,
     * the id of user to which the device belongs and the id of room where the device exists.
     *
     * @param userID
     * @param roomID
     * @param deviceID
     * @param callback
     */
    @GET("/f/users/{userID}/rooms/{roomID}/devices/{deviceID}")
    void findFavorite(@Path("userID") String userID, @Path("roomID") String roomID, @Path("deviceID") String deviceID, Callback<String> callback);

    /**
     * It changes the value of favorite attribute of a specific device given its id,
     * the id of user to which the device belongs and the id of room where the device exists.
     *
     * @param userID
     * @param roomID
     * @param deviceID
     * @param favorite
     * @param callback
     */
    @FormUrlEncoded
    @PUT("/users/{userID}/rooms/{roomID}/devices/{deviceID}")
    void addToFavorites(@Path("userID") String userID, @Path("roomID") String roomID, @Path("deviceID") String deviceID, @Field("device[favorite]") String favorite, Callback<Device> callback);
}


