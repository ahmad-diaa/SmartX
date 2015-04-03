package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import java.util.Iterator;
import java.util.List;

import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by maggiemoheb on 4/3/15.
 */
public class renameRoomActivity extends Activity {
    String ENDPOINT = "http://192.168.43.249:3000/";
    EditText roomName;
    int userID;
    int roomID;
    public void renameRoom(View view) {
        roomName = (EditText) findViewById(R.id.roomName);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.renameRoom(userID + "", roomID+"",roomName, new Callback<List<Room>>() {

            @Override
            public void success(List<Room> rooms, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }
}
