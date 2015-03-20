package com.smartx.cookies.smartx;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.AdapterView;

import java.util.List;

import models.Device;
import models.Room;
import models.User;
import models.Type;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * Created by zamzamy on 3/11/15.
 */
public interface myAPI {

    @FormUrlEncoded
    @POST("/api/v1/users")
    void login(@Field("user[name]") String username, @Field("user[password]") String password,
               Callback<User> callback);
    @FormUrlEncoded
    @POST("/api/v1/users/{userID}/rooms/")
    void addRoom(@Path("userID") String id, @Field("room[name]") String name
            ,@Field("room[photo]") String photo,@Field("room[room_id]") String room_id ,Callback<Room> callback);

    @GET("/api/v1/types/{name}")
    void requestBrands(@Path("name") String device, Callback<List<Type>> types);

    @FormUrlEncoded
    //@POST("/api/v1/users/{user_id}/rooms/{room_room_id}/devices")
//    void addDevice(@Path("user_id") String user_id, @Path("room_room_id") String room_room_id, @Field("device[name]") String name,
        //                 @Field("device[userID]") String userID, @Field("device[roomID]") String roomID,@Field("device[type]") String type, @Field("device[brand") String brand, Callback<Device> callback);
    @POST("/api/v1/users/1/rooms/1/devices")
    void addDevice(@Field("device[name]") String name,
                   @Field("device[user_id]") String userID, @Field("device[room_id]") String roomID,@Field("device[type_name]") String type, @Field("device[type_brand]") String brand, Callback<Device> callback);

    //@GET("/api/v1/users/{userID}/rooms/{roomID}")


    //void requestRoom(@Path("userID") int user, @Field(""));
//    @FormUrlEncoded
//    @POST("/api/v1/users/2/rooms")
//    void addRoom(@Field("room[room_id]") String id,@Field("room[name]") String roomName, @Field("room[photo]") String photo
//              , Callback<Room> callback);

//
//    @GET("/api/v1/users")
//    public void getFeed(Callback<List<User>> callback);

}
