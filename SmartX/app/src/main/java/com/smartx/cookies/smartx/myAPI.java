package com.smartx.cookies.smartx;

import java.util.List;
import models.Device;
import models.Room;
import models.Session;
import models.User;
import models.Type;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface myAPI {

    @FormUrlEncoded
    @POST("/session")
    void login(@Field("session[name]") String username, @Field("session[password]") String password,Callback<Session> callback);

    @GET("/users/{id}")
    public void getFeed(@Path("id") int id, Callback<User> callback);

    @FormUrlEncoded
    @POST("/users/{userID}/rooms/")
    void addRoom(@Path("userID") String id, @Field("room[name]") String name ,@Field("room[photo]") String photo,@Field("room[id]") String room_id ,Callback<Room> callback);

    @GET("/users/{userID}/rooms/{roomID}/devices")
    void viewDevices(@Path("userID") String id,@Path("roomID") String rid, Callback<List<Device>> callback);

    @GET("/types")
    void requestTypes(Callback<List<Type>> types);

    @FormUrlEncoded
    @POST("/users/{user_id}/rooms/{room_id}/devices")
    void addDevice(@Path("user_id") String userid, @Path("room_id") String roomid, @Field("device[name]") String name,@Field("device[device_id]") String device_id, @Field("device[status]") String status, Callback<Device> callback);

    @GET("/users/{userID}/rooms/")
    void viewRooms(@Path("userID") String id, Callback<List<Room>> callback);

    @FormUrlEncoded
    @PUT("/users/{userID}/")
    void changePassword(@Path("userID") String id, @Field ("user[password]") String password, Callback<User> callback);

    @GET("/users/{userID}/")
    void getUser (@Path("userID") String id, Callback <User> callback);
}
