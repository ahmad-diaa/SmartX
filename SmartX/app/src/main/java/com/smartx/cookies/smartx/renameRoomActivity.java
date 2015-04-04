package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_room);
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
            api.renameRoom(userID + "", roomID + "", name, new Callback<Room>() {

                @Override
                public void success(Room room, Response response) {
                    startActivity(new Intent(getApplicationContext(),ViewRooms.class));

                }

                @Override
                public void failure(RetrofitError error) {
                    throw error;
                }
            });
        }
  }


