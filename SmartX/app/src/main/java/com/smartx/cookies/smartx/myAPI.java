package com.smartx.cookies.smartx;

import java.util.List;
import java.util.concurrent.Callable;

import models.Device;
import models.Note;
import models.Plug;
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

/*
 *SE Sprint2
 *myAPI.java
 *Purpose: api interface to interact with rails.
 *
 *@author Amir, zamzamy, Dalia
 */

public interface myAPI {

    @GET("/v/users/{userID}/rooms/{roomName}")
    void findRoom(@Path("userID") String id, @Path("roomName") String name, Callback<List<Room>> callback);

    @GET("/users/{userID}/rooms/1/devices/1/devices")
    void allDevices(@Path("userID") String id, Callback<List<Device>> callback);

    @DELETE ("/session/{token}")
    void logout(@Path("token") String access_token,Callback<Session> callback);

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

    /**
     * updates the device's room_id to new id
     *
     * @param userId current userId
     * @param roomId current roomId
     * @param deviceId  curremt deviceID
     * @param newID new roomId
     * @param callback the updated device
     */
    @FormUrlEncoded
    @PUT("/users/{userId}/rooms/{roomId}/devices/{deviceId}/")
    void editDeviceRoom(@Path("userId") String userId, @Path("roomId") String roomId, @Path("deviceId") String deviceId, @Field("device[room_id]") String newID, Callback<Device> callback);

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
     * deletes device
     *
     * @param userID   the given userID
     * @param roomID   the given roomID
     * @param deviceID the given deviceID
     * @param callback the callback from the rails
     */

    @DELETE("/v/users/{userID}/rooms/{roomID}/devices/{deviceID}/")
    void deleteDevice(@Path("userID") String userID, @Path("roomID") String roomID, @Path("deviceID") String deviceID, Callback<Device> callback);

    @FormUrlEncoded
    @POST("/users/{userId}/rooms/{roomId}/plugs/")
    void addPlug(@Path("userId") String userId, @Path("roomId") String roomId, @Field("plug[plug_id]") String plugId, @Field("plug[name]") String name, @Field("plug[status]") String status, @Field("plug[photo]") String photo, Callback<Plug> callback);

    /**
     * Gets the plug with the
     *
     * @param userID   the given userID
     * @param roomID   the given roomID
     * @param plugID   the given plugID
     * @param callback the callback from the rails
     */
    @GET("/users/{userId}/rooms/{roomId}/plugs/{plugId}")
    void getPlug(@Path("userId") String userID, @Path("roomId") String roomID, @Field("plugId") String plugID, Callback <Plug> callback);

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

    /**
     * View all the plugs in the given roomID for the given userID
     *
     * @param userID   the given userID
     * @param roomID   the given roomID
     * @param callback the callback from the rails
     */
    @GET("/users/{userId}/rooms/{roomId}/plugs/")
    void viewPlugs(@Path("userId") String userID, @Path("roomId") String roomID, Callback<List<Plug>> callback);

    /**
     *
     * @param userID
     * @param roomID
     * @param callback
     */
    @GET("/users/{userId}/rooms/{roomId}/plugs/")
    void getPlugs(@Path("userId") String userID, @Path("roomId") String roomID, Callback <List<Plug>> callback);

    /**
    This method changes the status of a plug.
     *
     * @param userId
     * @param roomID
     * @param callback
     * @param plugID
     * @param status the status you want to change to
     */
    @FormUrlEncoded
    @PUT("/users/{userId}/rooms/{roomID}/plugs/{plugID}")
    void changePlugStatus(@Path("userId") String userId, @Path("roomID") String roomID, @Path("plugID") String plugID, @Field("plug[status]") String status, Callback<Plug> callback);

}


