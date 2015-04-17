package com.smartx.cookies.smartx.test;

/**
 * Created by Amir on 4/9/2015.
 */

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.addDevices;
import com.smartx.cookies.smartx.myAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import models.Type;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class addDevicesActivityTest extends ActivityInstrumentationTestCase2<addDevices> {

    private addDevices myActivity;

    private int userID;
    private int roomID;
    private String deviceID;
    private String deviceName;
    private String ENDPOINT;
    private List<String> types = new ArrayList<String>();

    public addDevicesActivityTest() {
        super(addDevices.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        userID = 3;
        roomID = 13;
        ENDPOINT = "http://50.0.32.231:3000/";
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }


    public void testGetTypesSuccess() throws Exception {
        types.add("Tv");
        types.add("Plug");
        types.add("Lamp");
        types.add("Radio");
        types.add("Receiver");
        types.add("Curtain");
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.requestTypes(new Callback<List<Type>>() {
            @Override
            public void success(List<Type> types, Response response) {
                List<String> typeNames = new ArrayList<String>();
                Iterator<Type> iterator = types.iterator();
                int i = types.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    typeNames.add(iterator.next().getName());
                    i--;
                }
                assertTrue(types.containsAll(typeNames) && typeNames.containsAll(types));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    public void testGetTypesFailure() throws Exception {
        types.add("Tv");
        types.add("Plug");
        types.add("Lamp");
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.requestTypes(new Callback<List<Type>>() {
            @Override
            public void success(List<Type> types, Response response) {
                List<String> typeNames = new ArrayList<String>();
                Iterator<Type> iterator = types.iterator();
                int i = types.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    typeNames.add(iterator.next().getName());
                    i--;
                }
                assertTrue(types.containsAll(typeNames) && typeNames.containsAll(types));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public void testAddDevicesSuccess() throws Exception {


        deviceID = "abc123";
        deviceName = "Tv";
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.addDevice(userID + " ", roomID + "", deviceName, deviceID, "off", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {

                api.viewDevices(userID + "", roomID + "", new Callback<List<Device>>() {
                    @Override
                    public void success(List<Device> devices, Response response) {
                        List<String> deviceIDs = new ArrayList<String>();
                        Iterator<Device> iterator = devices.iterator();
                        int i = devices.size() - 1;
                        while (i >= 0 & iterator.hasNext()) {
                            deviceIDs.add(iterator.next().getDeviceID());
                            i--;
                        }
                        assertTrue(deviceIDs.contains(deviceID));

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

    public void testAddDevicesFailure() throws Exception {
        deviceID = "abc123";
        deviceName = "Tv";
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.addDevice(userID + " ", roomID + "", deviceName, deviceID, "off", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {

                api.viewDevices(userID + "", roomID + "", new Callback<List<Device>>() {
                    @Override
                    public void success(List<Device> devices, Response response) {
                        List<String> deviceIDs = new ArrayList<String>();
                        Iterator<Device> iterator = devices.iterator();
                        int i = devices.size() - 1;
                        while (i >= 0 & iterator.hasNext()) {
                            deviceIDs.add(iterator.next().getDeviceID());
                            i--;
                        }
                        assertTrue(deviceIDs.contains("ab"));

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