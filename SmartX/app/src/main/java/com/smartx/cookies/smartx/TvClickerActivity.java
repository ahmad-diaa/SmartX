package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TvClickerActivity extends Activity {

    String ENDPOINT = "http://84.233.103.179:3000/";
    int userID;
    int roomID;
    int deviceID;
    String command ="Tv";
    int onAndOff =1;
    SharedPreferences mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_clicker);
       mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        deviceID = (mSharedPreference.getInt("deviceID", 1));
        Clicker TvClicker = new Clicker("1","1","1",command);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
//        api.getClicker(TvClicker.getUser_id(),TvClicker.getRoom_id(),TvClicker.getDevice_id(),TvClicker.getCommand(),new Callback<Clicker>() {
        api.addClicker("1","1","1",TvClicker.getCommand(),new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
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
        ClickedButton();
    }

    public void VolumeDown(View v){
        command = new String("TV/V/0");
        ClickedButton();
    }

    public void ChannelUP(View v){
        command = new String("TV/C/1");
        ClickedButton();
    }

    public void ChannelDown(View v){
        command = new String("TV/C/0");
        ClickedButton();
    }
    public void TurnOnandOff(View v){
        command = new String("TV/"+1+"");
        onAndOff++;
        ClickedButton();
    }

    public void ClickedButton() {
//        Clicker TvClicker = new Clicker(mSharedPreference.getString("userID",""),mSharedPreference.getString("roomID",""),
//                mSharedPreference.getString("deviceID",""), command);
        Clicker TvClicker = new Clicker("1","1","0",command);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
//        api.getClicker(TvClicker.getUser_id(),TvClicker.getRoom_id(),TvClicker.getDevice_id(),TvClicker.getCommand(),new Callback<Clicker>() {
        api.ChangeCVT("16", "1", "1", "1", TvClicker.getCommand(), new Callback<Clicker>() {
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
