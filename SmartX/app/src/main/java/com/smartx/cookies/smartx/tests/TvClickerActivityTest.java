package com.smartx.cookies.smartx.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.Switch;

import com.smartx.cookies.smartx.Clicker;
import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.TvClickerActivity;
import com.smartx.cookies.smartx.myAPI;

import models.Device;
import models.Room;
import models.Session;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Pupose:The purpose of this class is to test TVClickerActivity
 *
 * @author youmna
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
    private String ENDPOINT = "192.168.1.3";

    public TvClickerActivityTest() {
        super(TvClickerActivity.class);
    }

    /**
     * initiate parameters and get activities
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        previousChannel = (Button) myActivity.findViewById(R.id.button);
        nextChannel = (Button) myActivity.findViewById(R.id.button2);
        volumeUp = (Button) myActivity.findViewById(R.id.button3);
        volumeDown = (Button) myActivity.findViewById(R.id.button4);
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


    /**
     * verify that the test fixture has been set up correctly,
     * and the objects that you want to test have been correctly instantiated or initialized
     */
    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }

    /**
     * verifies that the protection is set correctly
     *
     * @throws Exception
     */
    public void testTurnOnOff() throws Exception {
                myActivity.TurnOnOff(myActivity.getWindow().getDecorView());
                onOff = (Switch) myActivity.findViewById(R.id.switch1);
                assertEquals(false, onOff.isEnabled());
    }

    /**
     * used to test if the clicker's current command changes when the channel "+" button is clicked
     * and the device is turned on
     *
     * @throws Exception
     */

    public void testNextChannelSuccess() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus("1", "1", "1", "1", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
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
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("1/C/1", clicker.getCommand());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    /**
     * test if the clicker's current command changes when the channel "-" button is clicked
     * and the device is turned on
     *
     * @throws Exception
     */
    public void testPreviousChannelSuccess() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus("1", "1", "1", "1", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
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
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("1/C/0", clicker.getCommand());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    /**
     * test if the clicker's current command changes when the Volume "+" button is clicked
     * and  the device is turned off
     *
     * @throws Exception
     */
    public void testVolumeUpFailure() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus("1", "1", "1", "0", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
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
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("1/V/1", clicker.getCommand());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    /**
     * used to test if the clicker's current command changes if the volume "-" button is clicked
     * and  the device is turned off
     *
     * @throws Exception
     */
    public void testVolumeDownFailure() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus("1", "1", "1", "0", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff = (Switch) myActivity.findViewById(R.id.switch1);
                onOff.setChecked(false);
            }
        });

        myActivity.volumeDown(myActivity.getWindow().getDecorView());
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                api.getDevice("1", "1", "1", new Callback<Device>() {
                    @Override
                    public void success(Device device, Response response) {
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                assertNotSame(clicker.getCommand(), "1/V/0");
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("error", "error");
            }
        });
    }

    /**
     * test if the clicker's current command changes when the channel "+" button is clicked
     * and the status = "0"
     *
     * @throws Exception
     */
    public void testNextChannelFailure() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus("1", "1", "1", "0", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
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
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("1/C/1", clicker.getCommand());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    /**
     * test the clicker's current command changes when the channel "+" button is clicked
     * and the device is turned on
     *
     * @throws Exception
     */
    public void testPreviousChannelFailure() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus("1", "1", "1", "0", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
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
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertNotSame("1/C/0", clicker.getCommand());
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("error", "error");
            }
        });
    }

    /**
     * test the clicker's current command changes when the volume "+" button is clicked
     * and the device is turned on
     *
     * @throws Exception
     */
    public void testVolumeUpSuccess() throws Exception {
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
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(true);
            }
        });
        myActivity.volumeUP(myActivity.getWindow().getDecorView());
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("1/V/1", clicker.getCommand());
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("error", "error");
            }
        });
    }

    /**
     * test the clicker's current command changes when the volume "-" button is clicked
     * and the device is turned on
     *
     * @throws Exception
     */
    public void testVolumeDownSuccess() throws Exception {
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
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                onOff.setChecked(true);
            }
        });
        myActivity.volumeDown(myActivity.getWindow().getDecorView());
        api.getClicker("1", "1", "1", new Callback<com.smartx.cookies.smartx.Clicker>() {
            @Override
            public void success(com.smartx.cookies.smartx.Clicker clicker, Response response) {
                assertEquals("1/V/0", clicker.getCommand());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
