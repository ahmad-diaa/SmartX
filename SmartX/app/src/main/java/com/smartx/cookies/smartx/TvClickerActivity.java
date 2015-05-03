package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * purpose:This class creates an instance of a clicker that will allow the user to control a device.
 *
 * @author youmna
 */
public class TvClickerActivity extends Activity {
    int userID; //store the current userID
    int roomID;//store the current roomID
    String deviceID;//store the current deviceID
    int clickerID;//store the current clickerID
    String command;//store the current command
    boolean on;//initial current state of device
    SharedPreferences mSharedPreference;//Used to get data from previous sessions
    Clicker tvClicker;
    Switch mySwitch;// the Switch instance of this activity

    /**
     * clickerId getter
     *
     * @return clickerId
     */
    public int getClickerID() {
        return clickerID;
    }

    /**
     * ENDPOINT getter
     *
     * @return ENDPOINT
     */
    public String getENDPOINT() {
        return getResources().getString(R.string.ENDPOINT);
    }

    /**
     * userId getter
     *
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * roomId getter
     *
     * @return RoomID
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * deviceId getter
     *
     * @return deviceID
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * command getter
     *
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * current status getter
     *
     * @return status
     */
    public boolean getOn() {
        return on;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvClicker = new Clicker();
        setContentView(R.layout.activity_tv_clicker);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        deviceID = (mSharedPreference.getString("deviceID", "1"));
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
                tvClicker = new Clicker(clicker.getUserId(), clicker.getRoomId(), clicker.getDeviceId(), clicker.getClickerId(), clicker.getCommand());
                clickerID = clicker.getClickerId();
                checkPreviousState();


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        checkPreviousState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tv_clicker, menu);
        return true;
    }

    /**
     * called  if the Volume "+" button in tv_clicker layout is clicked.
     * It updates the current clicker command to the recently entered one by calling sendCommand method
     *
     * @param v
     */
    public void volumeUP(View v) {
        command = new String(deviceID + "/V/1");
        if (on)
            sendCommand();
        else
            Toast.makeText(getApplicationContext(), "Device is turned off", Toast.LENGTH_LONG).show();

    }

    /**
     * called  if the Volume "-" button in tv_clicker layout is clicked.
     * It updates the current clicker command to the recently entered one by calling sendCommand method
     *
     * @param v
     */
    public void volumeDown(View v) {

        command = new String(deviceID + "/V/0");
        if (on)
            sendCommand();
        else
            Toast.makeText(getApplicationContext(), "Device is turned off", Toast.LENGTH_LONG).show();

    }

    /**
     * called  if the channel "+" button in tv_clicker layout is clicked.
     * It updates the current clicker command to the recently entered one by calling sendCommand method
     *
     * @param v
     */
    public void nextChannel(View v) {

        command = new String(deviceID + "/C/1");
        if (on)
            sendCommand();
        else
            Toast.makeText(getApplicationContext(), "Device is turned off", Toast.LENGTH_LONG).show();


    }

    /**
     * called  if the channel "+" button in tv_clicker layout is clicked.
     * It updates the current clicker command to the recently entered one by calling sendCommand method
     *
     * @param v
     */
    public void previousChannel(View v) {
        command = new String(deviceID + "/C/0");
        if (on)
            sendCommand();
        else
            Toast.makeText(getApplicationContext(), "Device is turned off", Toast.LENGTH_LONG).show();


    }

    /**
     * called  if the Switch "on/off" button in tv_clicker layout is clicked.
     * It updates the current device status to either on or off by calling changeDeviceStatus method
     * if the device was turned off then the command wont be sent otherwise sendCommand method
     * will send the current command to the Clicker
     *
     * @param v
     */
    public void TurnOnOff(View v) {
        on = !on;
        command = new String(deviceID + "/" + on + "");
        sendCommand();
        mySwitch.setEnabled(false);
        runOnUiThread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000000000; i++) ;
                for (int i = 0; i < 1000000000; i++) ;
                for (int i = 0; i < 1000000000; i++) ;
                for (int i = 0; i < 1000000000; i++) ;
                for (int i = 0; i < 1000000000; i++) ;
                for (int i = 0; i < 1000000000; i++) ;
                mySwitch.setEnabled(true);
            }
        });
    }

    /**
     * called  each time the tv_clicker layout is loaded to update the switch state to on or off according
     * to the device state before the Clicker was closed last time.
     */
    public void checkPreviousState() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.getDevice(userID + "", roomID + "", deviceID + "", new Callback<Device>() {

            @Override
            public void success(Device device, Response response) {
                Switch onOff = (Switch) findViewById(R.id.switch1);
                if (device.getStatus().contains("true")) {
                    onOff.setChecked(true);
                    on = true;
                } else {
                    onOff.setChecked(false);
                    on = false;
//                TvClicker = new Clicker(device.getUserID(), device.getRoomID(), Integer.parseInt(device.getDeviceId()), 0, "");
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    /**
     * changes the status of the device when switch is turned on/off
     *
     * @param on
     */
    public void changeDeviceStatus(boolean on) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.editDeviceStatus(userID + "", roomID + "", deviceID, on + "", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    /**
     * called if the device was switched on ,it updates the current clicker command to the recently entered one
     */
    public void sendCommand() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.sendClickerCommand(userID + "", roomID + "", deviceID, clickerID + "", command, new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
                if (command.contains("V/0")) {
                    Toast.makeText(getApplicationContext(), "Volume down", Toast.LENGTH_LONG).show();
                }
                if (command.contains("V/1")) {
                    Toast.makeText(getApplicationContext(), "Volume up", Toast.LENGTH_LONG).show();
                }
                if (command.contains("C/0")) {
                    Toast.makeText(getApplicationContext(), "Previous Channel", Toast.LENGTH_LONG).show();
                }
                if (command.contains("C/1")) {
                    Toast.makeText(getApplicationContext(), "Next Channel", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong with the clicker!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
