package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TvClickerActivity extends Activity {

    String ENDPOINT = "http://192.168.1.4:3000/";
    int userID;
    int roomID;
    int deviceID;
    String command ="Tv";
    int on_and_off =1;
    SharedPreferences mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_clicker);
       mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        deviceID = (mSharedPreference.getInt("deviceID", 1));
        final Clicker TvClicker = new Clicker("1","1","1",command);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

        myAPI api = adapter.create(myAPI.class);
//        api.getClicker(TvClicker.getUser_id(),TvClicker.getRoom_id(),TvClicker.getDevice_id(),TvClicker.getCommand(),new Callback<Clicker>() {
        api.get_clicker("2", "2", "3", new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
//                TvClicker.setDevice_id(clicker.getDevice_id());
//                TvClicker.setCommand(clicker.getCommand());
//                TvClicker.setRoom_id(clicker.getRoom_id());
//                TvClicker.setUser_id(clicker.getUser_id());
//                startActivity(new Intent(TvClickerActivity.this, About_us.class));

            }

            @Override
            public void failure(RetrofitError error) {
//                startActivity(new Intent(TvClickerActivity.this, About_us.class));


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
    public void VolumeUP(View v){
        command = new String("TV/");
        command+="V/1";
//        ClickedButton();
        in_common();
    }

    public void VolumeDown(View v){
        command = new String("TV/V/0");
//        ClickedButton();
        in_common();
    }

    public void ChannelUP(View v){
        command = new String("TV/C/1");
//        ClickedButton();
        in_common();

    }

    public void ChannelDown(View v){
        command = new String("TV/C/0");
//        ClickedButton();
        in_common();

    }
    public void TurnOnandOff(View v){
        command = new String("TV/"+ on_and_off %2+"");
        on_and_off++;
//        ClickedButton();
        if(on_and_off%2 !=0)
            in_common();

        change_device_status(on_and_off%2);

    }
    public void check_previous_state(){
        Clicker TvClicker = new Clicker("2","2","3",command);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewDevice("2","2","3",new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
                Switch on_off = (Switch) findViewById(R.id.switch1);

                if(device.getStatus().contains("0"))
                    on_off.setChecked(false);
                else
                    on_off.setChecked(true);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    public void change_device_status(int status){
        Clicker TvClicker = new Clicker("2","2","3",command);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.edit_device_status("2","2","3",status+"",new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(TvClickerActivity.this, About_us.class));

            }
        });
    }
    public void ClickedButton() {
////        Clicker TvClicker = new Clicker(mSharedPreference.getString("userID",""),mSharedPreference.getString("roomID",""),
////                mSharedPreference.getString("deviceID",""), command);
//        Clicker TvClicker = new Clicker("2","2","3",command);
//        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
//        myAPI api = adapter.create(myAPI.class);
////        api.getClicker(TvClicker.getUser_id(),TvClicker.getRoom_id(),TvClicker.getDevice_id(),TvClicker.getCommand(),new Callback<Clicker>() {
//        api.send_clicker_command("2", "2", "3", "32",command, new Callback<Clicker>() {
//            @Override
//            public void success(Clicker clicker, Response response) {
////                startActivity(new Intent(TvClickerActivity.this, About_us.class));
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                startActivity(new Intent(TvClickerActivity.this, About_us.class));
//
//            }
//        });

    }
    public void in_common(){
        //        Clicker TvClicker = new Clicker(mSharedPreference.getString("userID",""),mSharedPreference.getString("roomID",""),
//                mSharedPreference.getString("deviceID",""), command);
        Clicker TvClicker = new Clicker("2","2","3",command);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
//        api.getClicker(TvClicker.getUser_id(),TvClicker.getRoom_id(),TvClicker.getDevice_id(),TvClicker.getCommand(),new Callback<Clicker>() {
        api.send_clicker_command("2", "2", "3", "32",command, new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
//                startActivity(new Intent(TvClickerActivity.this, About_us.class));

            }

            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(TvClickerActivity.this, About_us.class));

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
