package com.smartx.cookies.smartx.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Switch;

import com.smartx.cookies.smartx.Clicker;
import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.defaultClickerActivity;
import com.smartx.cookies.smartx.myAPI;

import models.Device;
import models.Room;
import models.Session;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by youmna on 5/1/15.
 */
public class defaultClickerActivityTest extends ActivityInstrumentationTestCase2<defaultClickerActivity> {
    private defaultClickerActivity myActivity;//TvClickerACtivity
    private Switch onOff;//On and off switch
    private String ENDPOINT = "217.53.101.235";

    public defaultClickerActivityTest() {
        super(defaultClickerActivity.class);
    }
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        onOff = (Switch) myActivity.findViewById(R.id.switch1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.login("ahdiaa", "123456", new Callback<Session>() {
            @Override
            public void success(Session session, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        api.addRoom("1", "Kitchen", new Callback<Room>() {
            @Override
            public void success(Room room, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        api.addDevice("1", "1", "1", "1", "0", new Callback<Device>() {

            @Override
            public void success(Device device, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        api.addClicker("1", "1", "1", "1", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }
    /**
     * test the clicker's current command changes when the volume "-" button is clicked
     * and the device is turned on
     *
     * @throws Exception
     */
    public void OnOff() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus("1", "1", "1", "1", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        myActivity.TurnOnOff(myActivity.getWindow().getDecorView());
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("1/true", clicker.getCommand());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}

