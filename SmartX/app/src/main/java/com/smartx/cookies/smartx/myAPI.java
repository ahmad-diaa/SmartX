package com.smartx.cookies.smartx;

import android.widget.EditText;

import java.util.List;

import models.Device;
import models.Room;
import models.Session;
import models.User;
import models.Type;
import retrofit.Callback;
import retrofit.http.DELETE;
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
    @POST("/users/{userID}/rooms/")

    void addRoom(@Path("userID") String id, @Field("room[name]") String name , @Field("room[id]") String room_id ,Callback<Room> callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/rooms/{id}")
    void renameRoom(@Path("userID") String id,@Path("id") String roomID, @Field("room[name]") String name , Callback<Room> callback);

    @GET("/types/{name}")
    void requestBrands(@Path("name") String device, Callback<List<Type>> types);

    @GET("/users/{userID}/rooms/{roomID}/devices")
    void viewDevices(@Path("userID") String id, @Path("roomID") String rid, Callback<List<Device>> callback);

    @GET("/types")
    void requestTypes(Callback<List<Type>> types);

    @FormUrlEncoded
    @POST("/users/{user_id}/rooms/{room_id}/devices")
    void addDevice(@Field("device[name]") String name, @Field("device[user_id]") String userID, @Field("device[room_id]") String roomID,@Field("device[type_name]") String type, @Field("device[type_brand]") String brand, Callback<Device> callback);

    @GET("/users/{userID}/rooms/")
    void viewRooms(@Path("userID") String id, Callback<List<Room>> callback);

    @GET("/users/{userID}/")
    void getUser(@Path("userID") String id, Callback<User>callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/")
    void changePassword(@Path("userID") String id, @Field("user[password]") String password, Callback<User> callback);

    @DELETE("/users/{userID}/rooms/{roomID}")
    void deleteRoom(@Path("userID") String userid, @Path("roomID") String roomid, Callback<Room> callback);
}

