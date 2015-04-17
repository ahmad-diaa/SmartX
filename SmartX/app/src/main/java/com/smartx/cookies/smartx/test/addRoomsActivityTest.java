package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.addRoomsActivity;
import com.smartx.cookies.smartx.myAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Amir on 4/9/2015.
 */
public class addRoomsActivityTest extends ActivityInstrumentationTestCase2<addRoomsActivity> {


    private addRoomsActivity myActivity;

    private int userID;
    private String roomName;
    private String ENDPOINT;


    public addRoomsActivityTest() {
        super(addRoomsActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        userID = 1;
        ENDPOINT ="http://50.0.32.231:3000/";
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }
    public void testViewRoomsSuccess() throws Exception {

        roomName="LivingRoom";
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.addRoom(userID + "", roomName, new Callback<Room>() {
            @Override
            public void success( Room room, Response response) {

                api.viewRooms(userID + "", new Callback<List<Room>>() {
                    @Override
                    public void success(List<Room> rooms, Response response) {
                        List<String> roomNames = new ArrayList<String>();
                        Iterator<Room> iterator = rooms.iterator();
                        int i = rooms.size() - 1;
                        while (i >= 0 & iterator.hasNext()) {
                            roomNames.add(iterator.next().getName());
                            i--;
                        }
                        assertTrue( roomNames.contains(roomName));

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    public void testViewRoomsFailure() throws Exception {

        roomName="LivingRoom";
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.addRoom(userID + "", roomName, new Callback<Room>() {
            @Override
            public void success( Room room, Response response) {

                api.viewRooms(userID + "", new Callback<List<Room>>() {
                    @Override
                    public void success(List<Room> rooms, Response response) {
                        List<String> roomNames = new ArrayList<String>();
                        Iterator<Room> iterator = rooms.iterator();
                        int i = rooms.size() - 1;
                        while (i >= 0 & iterator.hasNext()) {
                            roomNames.add(iterator.next().getName());
                            i--;
                        }
                        assertTrue( roomNames.contains(""));

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
