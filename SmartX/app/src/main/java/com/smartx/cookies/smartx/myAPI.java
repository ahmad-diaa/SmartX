package com.smartx.cookies.smartx;


<<<<<<< HEAD
import models.Room;
import models.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;


=======
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

>>>>>>> SX1_user_can_login
/**
 * Created by zamzamy on 3/11/15.
 */
public interface myAPI {
    @FormUrlEncoded
<<<<<<< HEAD
    @POST("/api/v1/users")
    void login(@Field("user[name]") String username, @Field("user[password]") String password,
               Callback<User> callback);
    @FormUrlEncoded
    @POST("/api/v1/users/{userID}/rooms/")
    void addRoom(@Path("userID") String id, @Field("room[name]") String name
            ,@Field("room[photo]") String photo,@Field("room[room_id]") String room_id ,Callback<Room> callback);
//    @FormUrlEncoded
//    @POST("/api/v1/users/2/rooms")
//    void addRoom(@Field("room[room_id]") String id,@Field("room[name]") String roomName, @Field("room[photo]") String photo
//              , Callback<Room> callback);

//
//    @GET("/api/v1/users")
//    public void getFeed(Callback<List<User>> callback);
=======
    @POST("/session")
    void login(@Field("session[name]") String username, @Field("session[password]") String password,
               Callback<Session> callback);


    @GET("/users/{id}")
    public void getFeed(@Path("id") int id, Callback<User> callback);
>>>>>>> SX1_user_can_login

}
