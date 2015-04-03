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
    public static int count = -1;
    String ENDPOINT = "http://192.168.43.249:3000/";
    int userID;
    int[] photos = new int[]{ R.drawable.one ,
            R.drawable.two ,R.drawable.three ,R.drawable.four ,R.drawable.five ,
            R.drawable.six ,R.drawable.seven ,R.drawable.eight ,R.drawable.nine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrooms);
        final SharedPreferences  mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID=(mSharedPreference.getInt("userID", 1));
        addRoomButton = (Button) findViewById(R.id.addRoomButton);
    }

    public int randomIcon(){
         count = (count + 1)%9 ;
         return count;
    }

    public void addRoomButton(View v) {
        roomName = (EditText) findViewById(R.id.roomName);
        roomID = (EditText) findViewById(R.id.RoomIDText);
        Button addRoomButton = (Button) v;
        Room room = new Room(roomName.getText().toString(),roomID.getText().toString());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        room.setPhoto(photos[randomIcon()] + "");
        api.addRoom((userID + ""), room.get_roomName(), room.getPhoto(), room.get_id(), new Callback<Room>() {

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
