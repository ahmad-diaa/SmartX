package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class defaultClickerActivity extends ActionBarActivity {

    int userID; //store the current userID
    int roomID;//store the current roomID
    String deviceID;//store the current deviceID
    int clickerID;//store the current clickerID
    String command;//store the current command
    boolean on;//initial current state of device
    SharedPreferences mSharedPreference;//Used to get data from previous sessions
    Clicker plugClicker;

    /**
     * clickerId getter
     *
     * @return clickerId
     */
    public int getClickerID() {
        return clickerID;
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
        plugClicker = new Clicker();
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
                plugClicker = new Clicker(clicker.getUserId(), clicker.getRoomId(), clicker.getDeviceId(), clicker.getClickerId(), clicker.getCommand());
                clickerID = clicker.getClickerId();
                checkPreviousState();


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        checkPreviousState();
    }
    public void checkPreviousState() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.getDevice(userID + "", roomID + "", deviceID + "", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {

                Switch on_off = (Switch) findViewById(R.id.switch1);

                if (device.getStatus().contains("true")) {
                    on_off.setChecked(true);
                    on = true;


                } else {
                    on_off.setChecked(false);
                    on = false;
//                plugClicker = new Clicker(device.getUserID(), device.getRoomID(), Integer.parseInt(device.getDeviceId()), 0, "");
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default_clicker, menu);
        return true;
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
    public void TurnOnOff(View v) {
        on = !on;
        command = new String(deviceID+"/" + on + "");
        sendCommand();
        changeDeviceStatus(on);

    }

    public void changeDeviceStatus(boolean on) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        //editPlugStatus
        api.editDeviceStatus(userID + "", roomID + "", deviceID, on + "", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(getApplicationContext(), About_us.class));

            }
        });
    }

    /**
     * called if the device was switched on ,it updates the current clicker command to the recently entered one
     */
    public void sendCommand() {
        RestAdapter adapter =new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();;
        myAPI api = adapter.create(myAPI.class);
        //call Plug controller
        api.sendClickerCommand(userID + "", roomID + "", deviceID, clickerID + "",command, new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {

            }


            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong with the clicker!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
