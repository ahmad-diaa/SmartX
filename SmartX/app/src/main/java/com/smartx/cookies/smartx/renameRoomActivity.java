package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RenameRoomActivity extends Activity {

    String ENDPOINT = "http://192.168.43.249:3000/";
    EditText roomName;
    int userID;
    int roomID;
    String errorMessage = "initial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_room);
    }

    public String getENDPOINT() {

        return this.ENDPOINT;
    }

    public int getUserID() {

        return this.userID;
    }

    public int getRoomID() {
        return this.roomID;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rename_room, menu);
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

    public void renameRoom(View view) {
        roomName = (EditText) findViewById(R.id.rename_room);
        String name = String.valueOf(roomName.getText());
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        if (name.equals("")) {
            errorMessage = "Room name cannot be blank";
        } else {
            errorMessage = "";
        }
        api.renameRoom(userID + "", roomID + "", name, new Callback<Room>() {

            @Override
            public void success(Room room, Response response) {

                startActivity(new Intent(getApplicationContext(), viewDevices.class));

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Room name cannot be blank", Toast.LENGTH_LONG).show();


            }
        });
    }
}


