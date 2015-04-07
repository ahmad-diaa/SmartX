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

/*
This class creates an instance of a clicker that will allow the user to control a device.

 */
public class TvClickerActivity extends Activity {
    String end_point = "http://192.168.1.4:3000/";
    int userID;
    int roomID;
    int deviceID;
    String command = "Tv";
    int on_and_off = 1;
    SharedPreferences mSharedPreference;
    Clicker TvClicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* This method is called when the app is run
        and it is expected to create the a TV controller view
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_clicker);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        deviceID = (mSharedPreference.getInt("deviceID", 1));
        TvClicker = new Clicker("1", "1", "1", command);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(end_point).build();

        myAPI api = adapter.create(myAPI.class);
        api.get_clicker("2", "2", "3", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
                /*
                This method is called when retrofit succeed to get the requested clicker from rails.
                 */
            }

            @Override
            public void failure(RetrofitError error) {
                /*
                  Case retrofit fails to get the requested clicker from rails.
                 It Toasts a message to the user indicating that this device does not have a controller.
                 */

            }
        });
        check_previous_state();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tv_clicker, menu);
        return true;
    }

    /*
    called  if the Volume "+" button in tv_clicker layout is clicked.
     It updates the current clicker command to the recently entered one by calling send_a_command method
     */
    public void volume_uP(View v) {
        command = new String(TvClicker.getClicker_id() + "/");
        command += "V/1";
        send_a_command();
    }

    /*
     called  if the Volume "-" button in tv_clicker layout is clicked.
      It updates the current clicker command to the recently entered one by calling send_a_command method
      */
    public void volume_down(View v) {
        command = new String(TvClicker.getClicker_id() + "/" + "V/0");
        send_a_command();
    }

    /*
    called  if the channel "+" button in tv_clicker layout is clicked.
     It updates the current clicker command to the recently entered one by calling send_a_command method
     */
    public void next_channel(View v) {
        command = new String(TvClicker.getClicker_id() + "/" + "/C/1");
        send_a_command();

    }

    /*
    called  if the channel "+" button in tv_clicker layout is clicked.
     It updates the current clicker command to the recently entered one by calling send_a_command method
     */
    public void previous_channel(View v) {
        command = new String(TvClicker.getClicker_id() + "/" + "/C/0");
        send_a_command();

    }

    /*
      called  if the Switch "on/off" button in tv_clicker layout is clicked.
       It updates the current device status to either on or off by calling change_device_status method
         if the device was turned off then the command wont be sent otherwise send_a_command method
         will send the current command to the Clicker
       */
    public void Turn_on_off(View v) {
        command = new String(TvClicker.getClicker_id() + "/" + on_and_off % 2 + "");
        on_and_off++;
        if (on_and_off % 2 != 0)
            send_a_command();

        change_device_status(on_and_off % 2);

    }

        /*
        called  each time the tv_clicker layout is loaded to update the switch state to on or off according
        to the device state before the Clicker was closed last time.
         */
    public void check_previous_state() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(end_point).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewDevice("2", "2", "3", new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
                            /*
                called  if retrofit succeed to get the current device
               It sets the switch's current status to the corresponding device status
                 */
                Switch on_off = (Switch) findViewById(R.id.switch1);

                if (device.getStatus().contains("0"))
                    on_off.setChecked(false);
                else
                    on_off.setChecked(true);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
        /*
       changes the status of the device when switch is turned on/off
         */
    public void change_device_status(int status) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(end_point).build();
        myAPI api = adapter.create(myAPI.class);
        api.edit_device_status("2", "2", "3", status + "", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    /*
    called if the device was switched on ,it updates the current clicker command to the recently entered one
     */
    public void send_a_command() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(end_point).build();
        myAPI api = adapter.create(myAPI.class);
        api.send_clicker_command("2", "2", "3", "32", command, new Callback<Clicker>() {
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
