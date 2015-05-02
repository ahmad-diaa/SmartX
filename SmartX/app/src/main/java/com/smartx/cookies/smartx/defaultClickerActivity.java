package com.smartx.cookies.smartx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * purpose:This class creates an instance of a clicker that will allow the user to control a device.
 *
 * @author youmna
 */
public class defaultClickerActivity extends ActionBarActivity {
    int userID; //store the current userID
    int roomID;//store the current roomID
    String deviceID;//store the current deviceID
    int clickerID;//store the current clickerID
    String command;//store the current command
    boolean on;//initial current state of device
    SharedPreferences mSharedPreference;//Used to get data from previous sessions

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
        setContentView(R.layout.activity_default_clicker);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        deviceID = (mSharedPreference.getString("deviceID", "1"));
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
                clickerID = clicker.getClickerId();
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

    /**
     * called  if the Switch "on/off" button in clicker layout is clicked.
     * It updates the current device status to either on or off
     * it will send the current command to the Clicker
     *
     * @param v
     */
    public void TurnOnOff(View v) {
        on = !on;
        command = new String(deviceID + "/" + on + "");
        sendCommand();
    }

    /**
     * called if the device was switched on ,it updates the current clicker command to the recently entered one
     */
    public void sendCommand() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        //call Plug controller
        api.sendClickerCommand(userID + "", roomID + "", deviceID, clickerID + "", command, new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong with the clicker!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
