package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.ViewDeviceActivity;
import com.smartx.cookies.smartx.myAPI;
import com.smartx.cookies.smartx.viewDevices;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dalia on 5/1/15.
 */
public class deleteDeviceTest extends ActivityInstrumentationTestCase2<viewDevices> {
    private viewDevices myActivity;
    RestAdapter adapter;
    private int userID;//The user ID of the RenameRoomActivity
    private int roomID;//The room ID of the RenameRoomActivity
    myAPI api;
    String ENDPOINT;
    boolean flag = true;

    public deleteDeviceTest(){
        super(viewDevices.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        ENDPOINT = "http://197.161.9.40:3000/";
        userID = myActivity.getUserID();
        roomID = myActivity.getRoomID();
        adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        api = adapter.create(myAPI.class);
        // testdeleteDevice();
        // assertEquals(false, flag);
    }
    //Please put a device in room id = 1!
    public void testdeleteDevice() throws Exception{
        myActivity.deleteDevice(1, 1, 0);
        api.getDevice("1'", "1", myActivity.getDeviceIDTest(), new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
                assertEquals(1, 0);
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals(0, 0);
            }
        });
    }
}