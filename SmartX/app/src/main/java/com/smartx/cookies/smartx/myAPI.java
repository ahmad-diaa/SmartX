package com.smartx.cookies.smartx;


import models.Room;
import models.User;
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
    @POST("/session")
    void login(@Field("session[name]") String username, @Field("session[password]") String password,
               Callback<Session> callback);


    @GET("/users/{id}")
    public void getFeed(@Path("id") int id, Callback<User> callback);

    @FormUrlEncoded
    @POST("/api/v1/users/{userID}/rooms/")
    void addRoom(@Path("userID") String id, @Field("room[name]") String name
            , @Field("room[photo]") String photo, @Field("room[room_id]") String room_id, Callback<Room> callback);
//

}
