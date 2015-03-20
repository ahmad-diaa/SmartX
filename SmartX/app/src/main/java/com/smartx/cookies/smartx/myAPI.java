package com.smartx.cookies.smartx;


import java.util.List;

import models.Device;
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


    @GET("/api/v1/users/{userID}/rooms/{roomID}")
    void viewDevices(@Path("userID") String id,@Path("roomID") String rid, Callback <List<Device>> callback );



//
//    @GET("/api/v1/users")
//    public void getFeed(Callback<List<User>> callback);

}
