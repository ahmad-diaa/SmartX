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
public class LampClickerActivity extends ActionBarActivity {
    int userID; //store the current userID
    int roomID;//store the current roomID
    String deviceID;//store the current deviceID
    int clickerID;//store the current clickerID
    String command;//store the current command
    boolean on;//initial current state of device
    int brightness;//controls the brightness level
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
        setContentView(R.layout.activity_lamp_clicker);
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
        getMenuInflater().inflate(R.menu.menu_lamp_clicker, menu);
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
     * called  if the Switch "on/off" in lamp_clicker layout is clicked.
     * will send the current command to the Clicker
     *
     * @param v
     */
    public void TurnOnOff(View v) {
        on = !on;
        if (on)
            command = new String(deviceID + "/1");
        else
            command = new String(deviceID + "/0");
        sendCommand();
    }

    /**
     * called  if the button '+' in lamp_clicker layout is clicked.
     * will send the current command to the Clicker
     *
     * @param v
     */
    public void dim(View v) {
        if (brightness > 0) {
            brightness--;
            command = new String(deviceID + "d/1");
            Toast.makeText(getApplicationContext(), "dimming", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "lights are off", Toast.LENGTH_SHORT).show();
        }
        sendCommand();
    }

    /**
     * called  if the button '-' in lamp_clicker layout is clicked.
     * will send the current command to the Clicker
     *
     * @param v
     */
    public void bright(View v) {
        if (brightness < 10) {
            brightness++;
            command = new String(deviceID + "b/1");
            Toast.makeText(getApplicationContext(), "increase brightness", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), " max brightness", Toast.LENGTH_SHORT).show();
        }
        sendCommand();
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
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong with the clicker!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
