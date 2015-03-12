package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import models.Room;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class addRoomsActivity extends Activity{ //implements View.OnClickListener{

    Button addRoomButton;
    EditText roomName;
    TextView testRooms;
    public int count;
    String ENDPOINT = "http://192.168.1.106:3000/";
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrooms);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID=(mSharedPreference.getInt("userID", 1));
        addRoomButton = (Button) findViewById(R.id.addRoomButton);
       // addRoomButton.setOnClickListener(this);
        roomName = (EditText) findViewById(R.id.roomName);
        testRooms = (TextView) findViewById(R.id.testRooms);
       // dbHandler = new DBHandler(this, null, null, 1);
        printDatabase();
    }

    public void addRoomButton(View v){
        Button addRoomButton = (Button) v;
     //  dbHandler = new DBHandler(this, null, null, 1);
        Room room = new Room(roomName.getText().toString());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

        myAPI api = adapter.create(myAPI.class);
       // System.out.print("Respond2");
      //  api.addRoom(roomName.getText().toString(), "soora", userID +"", new Callback<Room> () {
            api.addRoom(userID +"", room, new Callback<Room>(){


            @Override
            public void success(Room room, Response response) {


            }

            @Override
            public void failure(RetrofitError error) {
//                throw error;
                startActivity(new Intent(getApplicationContext(),About_us.class));

            }
        });
     //   addRoomToDB(room);
        // printDatabase();
    }

//    public void addRoomToDB(Room room){
//
//        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
//
//        myAPI api = adapter.create(myAPI.class);
//        System.out.print("Respond2");
//        api.addRoom(Name, Pass, new Callback<User>() {
//
//
//            @Override
//            public void success(User user, Response response) {
//                startActivity(new Intent(LoginActivity.this,About_us.class));
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                throw error;
//
//            }
//        });
//
//
//
//    }



    //to delete FOR LATER!
//    public void deleteRoomButton(View view){
//        String inputText =buckysInput.getText().toString();
//        dbHandler.deleteRoom();
//    }

    public void printDatabase(){
       // String dbString = dbHandler.databaseToString();
     //   testRooms.setText(dbString);
        roomName.setText("");
    }
//
//    @Override
//    public void onClick(View v) {
//        Room room = new Room(roomName.getText().toString());
//        dbHandler.addRoom(room);
//    }
}
