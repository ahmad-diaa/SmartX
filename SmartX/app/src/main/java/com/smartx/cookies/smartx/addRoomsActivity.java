package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class addRoomsActivity extends Activity{
    Button addRoomButton;
    EditText roomID;
    EditText roomName;
    String ENDPOINT = "http://172.20.10.3:3000/";
    int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrooms);
        final SharedPreferences  mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID=(mSharedPreference.getInt("userID", 1));
        addRoomButton = (Button) findViewById(R.id.addRoomButton);
    }

    public void addRoomButton(View v) {
        roomName = (EditText) findViewById(R.id.roomName);
        roomID = (EditText) findViewById(R.id.RoomIDText);
        Button addRoomButton = (Button) v;
        Room room = new Room(roomName.getText().toString(),roomID.getText().toString());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.addRoom((userID + ""), room.get_roomName(), new Callback<Room>() {

            @Override
            public void success(Room room, Response response) {
                startActivity(new Intent(getApplicationContext(), ViewRooms.class));
            }

            @Override
            public void failure(RetrofitError error) {
            Toast.makeText(getApplicationContext(), "Cannot add room!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
