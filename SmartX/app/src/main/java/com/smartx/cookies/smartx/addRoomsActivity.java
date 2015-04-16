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

public class addRoomsActivity extends Activity {

    Button addRoomButton;
    EditText roomID;
    EditText roomName;
    int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrooms);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        addRoomButton = (Button) findViewById(R.id.addRoomButton);
    }

    public void addRoomButton(View v) {
        roomName = (EditText) findViewById(R.id.roomName);
        roomID = (EditText) findViewById(R.id.RoomIDText);
        Button addRoomButton = (Button) v;
        roomName.setText(roomName.getText());
        Room room = new Room(roomName.getText().toString().replace(" ","%20"));
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.addRoom((userID + ""), room.getName(), room.getId()+"", new Callback<Room>() {

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
