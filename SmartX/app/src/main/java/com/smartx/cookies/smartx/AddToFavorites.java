package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * SE Sprint2
 * AddToFavorites.java
 * Purpose: Add devices to favorites list.
 *
 * @author Amir
 */
public class AddToFavorites extends Activity {

    /**
     * Called when the activity is starting.
     * It requests from rails the current value of favorite attribute for the device clicked.
     * If favorite is true, a message says that the device already exits in favorites list,
     * otherwise the favorite attribute is to be changed from false to true and a message says
     * that the device is successfully added to favorites list.
     *
     * @param savedInstanceState if the activity is being
     *                           re-initialized after previously being shut down then
     *                           this Bundle  contains the data it most recently supplied.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int userID = mSharedPreference.getInt("userID", 1);
        int roomID = mSharedPreference.getInt("roomID", 1);
        String deviceID = mSharedPreference.getString("deviceID", "");
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.findFavorite(userID + "", roomID + "", deviceID, new Callback<String>() {

            @Override
            public void success(String favorite, Response response) {
                if (favorite.equals("true")) {
                    Toast.makeText(getApplicationContext(), "device already in favorites list!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), ViewRooms.class));
                    return;
                }

                final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                int userID = mSharedPreference.getInt("userID", 1);
                int roomID = mSharedPreference.getInt("roomID", 1);
                String deviceID = mSharedPreference.getString("deviceID", "");
                RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
                myAPI api = adapter.create(myAPI.class);
                api.addToFavorites(userID + "", roomID + "", deviceID, "true", new Callback<Device>() {

                    @Override
                    public void success(Device device, Response response) {
                        Toast.makeText(getApplicationContext(), "device added successfully in favorites list!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), ViewRooms.class));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "Cannot add device to favorites list!", Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Cannot add device to favorites list!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

