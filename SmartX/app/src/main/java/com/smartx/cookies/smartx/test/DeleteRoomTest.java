package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.ViewRooms;
import com.smartx.cookies.smartx.myAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.Path;

/**
 * Purpose: tests deleting room in ViewRooms.java
 * Created by zamzamy on 4/16/15.
 */
public class DeleteRoomTest extends ActivityInstrumentationTestCase2<ViewRooms> {


    private ViewRooms myActivity;
    private int userID;

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    private int roomID;
    private List<String> names=new ArrayList<String>();
    private String ENDPOINT;
    RestAdapter adapter;
    myAPI api;
    private String roomName;

    /**
     * A constructor that matches the super constructor
     */
    public DeleteRoomTest() {
        super(ViewRooms.class);
    }

    /**
     * setUp() sets the instance variables to match those of the ViewRoomsActivity it is testing
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        userID = 1;
        roomName= "AwesomeRoom";
        ENDPOINT = "http://172.20.10.4:3000";
        adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        api = adapter.create(myAPI.class);
        api.addRoom(1 + "", "AwesomeRoom", "JustAnotherRoomID", new Callback<Room>() {
            @Override
            public void success(Room room, Response response) {
                setRoomID(room.getId());

            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });



    }


    /**
     * tests that the name is updated correctly when given the correct parameters
     * @throws Exception
     */

    public void testDeleteRoomSuccess() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {

            }
        });

        api.deleteRoom(userID+"", roomID+"", new Callback<Room>(){

            @Override
            public void success(Room room, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }

        });
        api.viewRooms(userID+"", new Callback<List<Room>>(){


            @Override
            public void success(List<Room> rooms, Response response) {
                ArrayList<String> roomNames = new ArrayList<String>();
                Iterator<Room> iterator = rooms.iterator();
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames.add(iterator.next().getName());
                    i--;
                }

                assertTrue(!(roomNames.contains("AwesomeRoom")));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });



    }


}