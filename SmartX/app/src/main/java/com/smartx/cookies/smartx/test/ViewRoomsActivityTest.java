package com.smartx.cookies.smartx.test;

/**
 * Created by Amir on 4/7/2015.
 */

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

public class ViewRoomsActivityTest  extends ActivityInstrumentationTestCase2<ViewRooms> {

    private ViewRooms myActivity;

    private int userID;
    private int roomID;
    private List<String> names=new ArrayList<String>();
    private String ENDPOINT;
    private List<String> types=new ArrayList<String>();

    public ViewRoomsActivityTest() {
        super(ViewRooms.class);
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

    public void testViewDevicesSuccess() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {

            }
        });


        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);

                api.findRoom(userID + "", "sleeping", new Callback<List<Room>>() {
                    @Override
                    public void success(List<Room> rooms, Response response) {
                        roomID = (rooms.get(0).getId());
                        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
                        myAPI api = adapter.create(myAPI.class);
                        api.viewDevices(userID + "", roomID + "", new Callback<List<Device>>(){

                            @Override
                            public void success (List <Device> devices, Response response){
                                List<String> deviceNames = new ArrayList<String>();
                                Iterator<Device> iterator = devices.iterator();
                                int i = devices.size() - 1;
                                while (i >= 0 & iterator.hasNext()) {
                                    deviceNames.add(iterator.next().getName());
                                    i--;
                                }
                                assertTrue(types.containsAll(deviceNames) && deviceNames.containsAll(types));
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
    public void testViewDevicesFailure() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
          types.add("tv");
            }
        });


        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);

        api.findRoom(userID + "", "sleeping", new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {
                roomID = (rooms.get(0).getId());
                final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
                myAPI api = adapter.create(myAPI.class);
                api.viewDevices(userID + "", roomID + "", new Callback<List<Device>>() {

                    @Override
                    public void success(List<Device> devices, Response response) {
                        List<String> deviceNames = new ArrayList<String>();
                        Iterator<Device> iterator = devices.iterator();
                        int i = devices.size() - 1;
                        while (i >= 0 & iterator.hasNext()) {
                            deviceNames.add(iterator.next().getName());
                            i--;
                        }
                        assertTrue(types.containsAll(deviceNames) && deviceNames.containsAll(types));
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

    public void testViewRoomsSuccess() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                names.add("sleeping");
                names.add("Kitchen");
                names.add("Game");

            }
        });


        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewRooms(userID + "", new Callback<List<Room>>() {
            @Override
            public void success( List<Room> rooms, Response response) {
                List< String> roomNames= new ArrayList<String>();
                Iterator<Room> iterator = rooms.iterator();
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames.add( iterator.next().getName());
                    i--;
                }
                assertTrue(names.containsAll(roomNames) && roomNames.containsAll(names));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    public void testViewRoomsFailure() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                names.add("sleeping");

            }
        });
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewRooms(userID + "", new Callback<List<Room>>() {
            @Override
            public void success( List<Room> rooms, Response response) {
                List< String> roomNames= new ArrayList<String>();
                Iterator<Room> iterator = rooms.iterator();
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames.add( iterator.next().getName());
                    i--;
                }
                assertTrue(names.containsAll(roomNames) && roomNames.containsAll(names));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
