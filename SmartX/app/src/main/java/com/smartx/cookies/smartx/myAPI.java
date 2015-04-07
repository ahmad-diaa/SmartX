package com.smartx.cookies.smartx;


import java.util.List;

import models.Device;
import models.Room;
import models.Type;
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
    void addRoom(@Path("userID") String id, @Field("room[name]") String name ,@Field("room[photo]") String photo,Callback<Room> callback);


    @GET("/users/{userID}/rooms/{roomID}/devices")
    void viewDevices(@Path("userID") String user_id,@Path("roomID") String room_id, Callback<List<Device>> callback);

    @GET("/users/{user_id}/rooms/{room_id}/devices/{device_id}")
    void viewDevice(@Path("user_id") String user_id,@Path("room_id") String room_id,@Path("device_id") String device_id, Callback<Device> callback);

    @FormUrlEncoded
    @PUT("/users/{user_id}/rooms/{room_id}/devices/{device_id}/clickers/{clicker_id}")
    void send_clicker_command (@Path("user_id") String user_id, @Path("room_id") String room_id, @Path("device_id") String device_id,@Path("clicker_id") String Clicker_id,@Field("clicker[command]") String command , Callback <Clicker> callback);

    @GET("/types/{name}")
    void requestBrands(@Path("name") String device, Callback<List<Type>> types);

    @FormUrlEncoded
    @POST("/users/{user_id}/rooms/{room_id}/devices/{device_id}/clickers")
    void addClicker (@Path("user_id") String user_id, @Path("room_id") String room_id, @Path("device_id") String device_id,@Field("clicker[command]") String command , Callback <Clicker> callback);

    @GET("/users/{user_id}/rooms/{room_id}/devices/{device_id}/clickers")
    void get_clicker (@Path("user_id") String user_id, @Path("room_id") String room_id, @Path("device_id") String device_id, Callback <Clicker> callback);


    @FormUrlEncoded
    @PUT("/users/{user_id}/rooms/{room_id}/devices/{device_id}")
    void edit_device_status (@Path("user_id") String user_id, @Path("room_id") String room_id, @Path("device_id") String device_id,@Field("device[status]") String status , Callback <Clicker> callback);

    @FormUrlEncoded
    @POST("/users/{user_id}/rooms/{room_id}/devices")
    void addDevice(@Path("user_id") String userid, @Path("room_id") String roomid, @Field("device[name]") String name,@Field("device[type_name]") String type, @Field("device[type_brand]") String brand, Callback<Device> callback);


    @GET("/users/{userID}/rooms/")
    void viewRooms(@Path("userID") String id, Callback<List<Room>> callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/")
    void changePassword(@Path("userID") String id, @Field ("user[password]") String password, Callback<User> callback);

    @FormUrlEncoded
    @GET("/users/{userID}/")
    void getUser (@Path("userID") String id, Callback <User> callback);
}
