package com.smartx.cookies.smartx.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.Switch;

import com.smartx.cookies.smartx.Clicker;
import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.TvClickerActivity;
import com.smartx.cookies.smartx.myAPI;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by youmna on 4/7/15.
 */
public class TvClickerActivityTest extends ActivityInstrumentationTestCase2<TvClickerActivity> {

    private TvClickerActivity myActivity;//TvClickerACtivity
    private Switch onOff;//On and off switch
    private Button volumeUp;//Volume"+" control
    private Button volumeDown;//Volume "-" control
    private Button nextChannel;//Channel "+"control
    private Button previousChannel;//channel "-" control
    private int userID;//current user id
    private int roomID;//current room id
    private int deviceID;//current device id
    private int clickerID;//current Clicker id
    private String ENDPOINT;

    public TvClickerActivityTest() {

        super(TvClickerActivity.class);
    }
/*
initiate parameters and get activities
  @throw*/
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        onOff = (Switch) myActivity.findViewById(R.id.switch1);
        previousChannel = (Button) myActivity.findViewById(R.id.button);
        nextChannel = (Button) myActivity.findViewById(R.id.button2);
        volumeUp = (Button) myActivity.findViewById(R.id.button3);
        volumeDown = (Button) myActivity.findViewById(R.id.button4);

        userID = myActivity.getUserID();
        roomID = myActivity.getRoomID()==0?1:myActivity.getRoomID();
        deviceID = myActivity.getDeviceID();
        clickerID = myActivity.getClickerID();
        ENDPOINT = myActivity.getENDPOINT();
    }
    /*
    verify that the test fixture has been set up correctly,
     and the objects that you want to test have been correctly instantiated or initialized
    */
    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }
/*
used to test if the clicker's current command changes when the channel "+" button is clicked
and the device is turned on
@throw
*/

    public void testNextChannelSuccess() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus(userID + "", roomID + "", deviceID + "", clickerID+"", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
            onOff.setChecked(true);
            }
        });

        myActivity.nextChannel(myActivity.getWindow().getDecorView());

        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("Tv/C/1", clicker.getCommand());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    /*
 test if the clicker's current command changes when the channel "-" button is clicked
and the device is turned on
@throw
*/
    public void testPreviousChannelSuccess() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus(userID + "", roomID + "", deviceID + "",clickerID+"" , new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(true);
            }
        });

        myActivity.previousChannel(myActivity.getWindow().getDecorView());

        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("Tv/C/0", clicker.getCommand());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    /*
 test if the clicker's current command changes when the Volume "+" button is clicked
and  the device is turned off
@throw
*/
    public void testVolumeUpFailure() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
api.editDeviceStatus(userID + "", roomID + "", deviceID + "", "0", new Callback<Clicker>() {
    @Override
    public void success(Clicker clicker, Response response) {
    }

    @Override
    public void failure(RetrofitError error) {

    }
});
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(false);

            }
        });

        myActivity.volumeUP(myActivity.getWindow().getDecorView());

        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("Tv/V/1", clicker.getCommand());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    /*
used to test if the clicker's current command changes if the volume "-" button is clicked
and  the device is turned off
@throw
*/
    public void testVolumeDownFailure() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus(userID + "", roomID + "", deviceID + "", "0", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(false);
            }
        });

        myActivity.volumeDown(myActivity.getWindow().getDecorView());
        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                api.viewDevice(userID + "", roomID + "", deviceID + "", new Callback<Device>() {
                    @Override
                    public void success(Device device, Response response) {
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                assertNotSame(clicker.getCommand(), "TV/V/0");

            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("error", "error");
            }
        });
    }
    /*
 test if the clicker's current command changes when the channel "+" button is clicked
 and the status = "0"@throw
*/
    public void testNextChannelFailure() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus(userID + "", roomID + "", deviceID + "", "0", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(false);
            }
        });

        myActivity.nextChannel(myActivity.getWindow().getDecorView());

        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("Tv/C/1", clicker.getCommand());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    /*
 test the clicker's current command changes
  when the channel "+" button is clicked
and the device is turned on
@throw
*/
    public void testPreviousChannelFailure() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus(userID + "", roomID + "", deviceID + "", "0", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(false);
            }
        });

        myActivity.previousChannel(myActivity.getWindow().getDecorView());

        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertNotSame("Tv/C/0", clicker.getCommand());

            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("error", "error");
            }
        });
    }
    /*
test the clicker's current command changes
when the volume "+" button is clicked
and the device is turned on
@throw
*/
    public void testVolumeUpSuccess() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus(userID + "", "1", deviceID + "", "1", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("error", "error");

            }
        });
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(true);
            }
        });

        myActivity.volumeUP(myActivity.getWindow().getDecorView());

        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("Tv/V/1", clicker.getCommand());

            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("error", "error");

            }
        });
    }
    /*
test the clicker's current command changes
when the volume "-" button is clicked
and the device is turned on
@throw
*/
    public void testVolumeDownSuccess() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus(userID + "", roomID + "", deviceID + "", "1", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(true);
            }
        });

        myActivity.volumeDown(myActivity.getWindow().getDecorView());

        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("Tv/V/0", clicker.getCommand());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
