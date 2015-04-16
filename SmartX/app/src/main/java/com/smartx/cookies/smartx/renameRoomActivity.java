package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

/**
 * Purpose: renames a room
 * @author maggiemoheb
 */
public class renameRoomActivity extends Activity {

    EditText roomName;// EditText field to put the new name room in
    int userID;// the user ID of the session
    int roomID;// room ID to be renamed
    String errorMessage = "initial"; //an initial error message to indicate that the rename is done successfully

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_room);
    }

    /**
     * A getter to the ENDPOINT
     * @return the ENDPOINT of the activity
     */
    public String getENDPOINT() {

        return getResources().getString(R.string.ENDPOINT);
    }

    /**
     * A getter to the user ID
     * @return the user ID of the session
     */
    public int getUserID() {

        return this.userID;
    }

    /**
     * A getter to the room ID
     * @return the room ID of the activity
     */
    public int getRoomID() {
        return this.roomID;
    }

    /**
     * A getter to the errorMessage
     * @return the errorMessage of the activity
     */
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

    /**
     * RenameRoom method takes the new room name from the EditText field
     * checks that the room name is not "" to update error message
     * calls API.renameRoom to update the name in rails server
     * @param view takes the current view as a parameter
     */
    public void renameRoom(View view) {
        roomName = (EditText) findViewById(R.id.rename_room);
        String name = String.valueOf(roomName.getText());
        name.replace(" " , "%20");
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        if (name.equals("")) {
            errorMessage = "Room name cannot be blank";
        } else {
            errorMessage = "";
        }
        api.renameRoom(userID + "", roomID + "", name, new Callback<Room>() {

            @Override
            public void success(Room room, Response response) {

                startActivity(new Intent(getApplicationContext(), ViewRooms.class));

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Room name cannot be blank", Toast.LENGTH_LONG).show();
            }
        });
    }
}


