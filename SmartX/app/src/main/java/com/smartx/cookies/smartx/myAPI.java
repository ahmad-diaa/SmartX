package com.smartx.cookies.smartx;


import models.Room;
import models.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
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

    @POST("/api/v1/users/{userID}/rooms/")
    void addRoom(@Path("userID") String id, @Body Room room, Callback<Room> callback);
//    @FormUrlEncoded
//    @POST("/api/v1/users/{userID}/rooms/")
//    void addRoom(@Field("room[name]") String roomName, @Field("photo") String photo,
//                 @Field("id") String id, Callback<Room> callback);

//
//    @GET("/api/v1/users")
//    public void getFeed(Callback<List<User>> callback);

}
