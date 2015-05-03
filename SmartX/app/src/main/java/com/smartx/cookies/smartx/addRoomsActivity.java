package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * SE Sprint1
 * addRoomsActivity.java
 * Purpose: Add rooms.
 *
 * @author Amir
 */

public class addRoomsActivity extends Activity {

    /**
     * Button clicked to add a room.
     */
    private Button addRoomButton;

    /**
     * Used in randomizing photos for rooms.
     */
    private static int count = -1;

    /**
     * Holds user's id saved in shared preferences.
     */
    private int userID;


    /**
     * Holds 9 different photos for rooms.
     */
    private int[] photos = new int[]{R.drawable.one,
            R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine};


    /**
     * Called when the activity is starting.
     * Get userID from shared preferences and Get button from View.
     *
     * @param savedInstanceState if the activity is being
     *                           re-initialized after previously being shut down then
     *                           this Bundle  contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrooms);
        final SharedPreferences SHARED_PREFERENCE =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (SHARED_PREFERENCE.getInt("userID", 1));

    addRoomButton=(Button)findViewById(R.id.addRoomButton);
}

/**
 * This method is used to randomize photos for rooms.
 *
 * @return integer value from zero to eight to select a photo for the room.
 */
public int randomIcon(){
        count=(count+1)%9;
        return count;
        }

/**
 * This method is called when the button is clicked.
 addRoom method posts to rails a new room to be created for current user then
 Activity ViewRooms starts showing all user's rooms including the new one.
 *
 * @param v the view that was clicked.
 */
public void addRoomButton(View v){
        EditText roomName=(EditText)findViewById(R.id.roomName);
        Room room=new Room(roomName.getText().toString().replace(" ","%20"));
        RestAdapter adapter=new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api=adapter.create(myAPI.class);
        room.setPhoto(photos[randomIcon()]+"");

        api.addRoom((userID+""),room.getName(),new Callback<Room>(){
@Override
public void success(Room room,Response response){
        SharedPreferences prefs=
        PreferenceManager.getDefaultSharedPreferences(addRoomsActivity.this);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("roomID",room.getId());
        editor.commit();
        startActivity(new Intent(getApplicationContext(),ViewRooms.class));
        }

@Override
public void failure(RetrofitError error){
        Toast.makeText(getApplicationContext(), "Cannot add room!", Toast.LENGTH_LONG).show();

        }
        });

        }


/**
 *get the button clicked to add a room.
 *
 * @return button
 */
public Button getButton(){
        return addRoomButton;
        }

/**
 *set the button clicked to add a room.
 *
 * @param addRoomButton
 */
public void setButton(Button addRoomButton){
        this.addRoomButton=addRoomButton;
        }

/**
 *get photos for rooms.
 *
 * @return photos
 */
public int[]getPhotos(){
        return photos;
        }

/**
 *set photos for rooms.
 *
 * @param photos
 */
public void setPhotos(int[]photos){
        this.photos=photos;
        }

/**
 *get the endpoint composed of the ip address of network
 plus the port number of server.
 *
 * @return the endpoint
 */
public String getEndpoint(){
        return getResources().getString(R.string.ENDPOINT);
        }


/**
 *get id of the user.
 *
 * @return primary key of the user.
 */
public int getUserID(){
        return userID;
        }

/**
 *set id of the user.
 *
 * @param userID the primary key of the user.
 */
public void setUserID(int userID){
        this.userID=userID;
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
            Intent i = new Intent(addRoomsActivity.this, ViewRooms.class);
            startActivity(i);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
