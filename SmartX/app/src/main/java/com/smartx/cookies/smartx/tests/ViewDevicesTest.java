package com.smartx.cookies.smartx.tests;

/**
 * Created by youmna 
 */

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.myAPI;
import com.smartx.cookies.smartx.viewDevices;

import java.util.Iterator;
import java.util.List;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by youmna on 4/16/15.
 */
public class ViewDevicesTest extends ActivityInstrumentationTestCase2<viewDevices> {
    private viewDevices myActivity;

    private String ENDPOINT;
    RestAdapter adapter;
    myAPI api;

    public ViewDevicesTest() {
        super(viewDevices.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        ENDPOINT = "http://217.53.100.214:3000/";
        adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        api = adapter.create(myAPI.class);
    }
    public void testViewDevices() throws Exception{
        api.viewDevices("1", "1", new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                Iterator<Device> iterator = devices.iterator();
                int i = devices.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    final Device current = iterator.next();
                    api.findDevice("1", "1", current.getName(), new Callback<List<Device>>() {
                        @Override
                        public void success(List<Device> devices, Response response) {
                            if (devices != null)
                                assertEquals(current.getDeviceId(), devices.get(0).getDeviceId());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            assertEquals("1", "0");
                        }

                    });

                    i--;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("1", "0");
            }
        });
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);

    }


}

