package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.smartx.cookies.smartx.ViewDeviceActivity;
import com.smartx.cookies.smartx.myAPI;

import java.util.List;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author ahmaddiaa.
 */
public class moveDeviceTest extends ActivityInstrumentationTestCase2<ViewDeviceActivity> {

    private ViewDeviceActivity myActivity;
    private int userID;
    private int roomID;
    private String deviceID = "asda";
    private String ENDPOINT;

    public moveDeviceTest() {
        super(ViewDeviceActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        userID = 1;
        roomID = 2;
        ENDPOINT = "http://192.168.1.4:3000/";
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }


    /**
     * Compares the expected device to the notes returned from rails server.
     */
    public void testmoveDevice() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {

            }
        });


        final RestAdapter adapter =
                new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.editDeviceRoom(userID + "", roomID + "", deviceID, "1", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {

                final RestAdapter adapter =
                        new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
                myAPI api = adapter.create(myAPI.class);
                api.findDevice(userID + "", "1", "TV", new Callback<List<Device>>() {
                    @Override
                    public void success(List<Device> devices, Response response) {
                        assertEquals(1, 1);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        assertEquals("Device was not moved", 1, 0);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR ", error.getMessage());
            }
        });
    }

    /**
     * Tests whether the device will be moved to a room containing the same type or not.
     */
    public void testMoveDeviceAlreadyExists() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                userID = 1;
                roomID = 1;
            }
        });


        final RestAdapter adapter =
                new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.editDeviceRoom(userID + "", roomID + "", deviceID, "3", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {

                final RestAdapter adapter =
                        new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
                myAPI api = adapter.create(myAPI.class);
                api.findDevice(userID + "", "1", "TV", new Callback<List<Device>>() {
                    @Override
                    public void success(List<Device> devices, Response response) {
                        assertEquals(1, 1);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        assertEquals("Device should not be moved", 1, 0);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR ", error.getMessage());
            }
        });
    }

}
