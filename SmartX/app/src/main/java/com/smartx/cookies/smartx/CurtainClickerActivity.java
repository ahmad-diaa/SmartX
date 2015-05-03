package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

public class CurtainClickerActivity extends ActionBarActivity {
    int userID; //store the current userID
    int roomID;//store the current roomID
    String deviceID;//store the current deviceID
    int clickerID;//store the current clickerID
    String command;//store the current command
    boolean on;//initial current state of device
    SharedPreferences mSharedPreference;//Used to get data from previous sessions
    ImageButton image; //Curtains on/off button
    int count = 0; //counter for the max and min

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curtain_clicker);
        image = (ImageButton) findViewById(R.id.imageButton);
        image.setBackgroundResource(R.drawable.curtainsoff);
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

    /**
     * It turns device on/off and updates the current clicker command to the recently entered one by calling sendCommand method
     *
     * @param v
     */
    public void ShadeSun(View v) {
        on = !on;
        if (on) {
            image.setBackgroundResource(R.drawable.curtainsonb);
            command = new String(deviceID + "/cur/0");
        } else {
            image.setBackgroundResource(R.drawable.curtainsoff);
            command = new String(deviceID + "/cur/1");
        }
        sendCommand();
    }

    /**
     * It close the curtains gradually and updates the current clicker command to the recently entered one by calling sendCommand method
     *
     * @param v
     */
    public void Shade(View v) {
        if (count == 10) {
            image.setBackgroundResource(R.drawable.curtainsoff);
        } else {
            count++;
        }
        command = new String(deviceID + "/shade/1");
        sendCommand();
    }

    /**
     * It opens the curtains gradually and updates the current clicker command to the recently entered one by calling sendCommand method
     *
     * @param v
     */
    public void Sunny(View v) {
        if (count == 0) {
            image.setBackgroundResource(R.drawable.curtainsonb);
        } else {
            count--;
        }
        command = new String(deviceID + "/sun/0");
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
                if (command.contains("cur/1")) {
                    Toast.makeText(getApplicationContext(), "Have a cloudy day", Toast.LENGTH_SHORT).show();
                }
                if (command.contains("cur/0")) {
                    Toast.makeText(getApplicationContext(), "Have a sunny day", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong with the clicker!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_curtain_clicker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param keyCode The value in event.getKeyCode().
     * @param event Description of the key event.
     * @return If you handled the event, return true. If you want to allow the event
     *          to be handled by the next receiver, return false.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(CurtainClickerActivity.this, viewDevices.class);
            startActivity(i);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
