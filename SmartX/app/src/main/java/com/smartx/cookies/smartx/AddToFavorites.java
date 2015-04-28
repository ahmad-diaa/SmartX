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
 * Created by Amir on 4/28/2015.
 */
public class AddToFavorites extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int userID = mSharedPreference.getInt("userID", 1);
        int roomID = mSharedPreference.getInt("roomID", 1);
        String deviceID = mSharedPreference.getString("deviceID","");
        RestAdapter adapter=new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api=adapter.create(myAPI.class);
        api.findFavorite(userID+"",roomID+"",deviceID,new Callback<String>(){

            @Override
            public void success(String favorite,Response response){
                if(favorite.equals("true")){
                    Toast.makeText(getApplicationContext(), "device already in favorites list!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), ViewRooms.class));
                    return;
                }

                final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                int userID = mSharedPreference.getInt("userID", 1);
                int roomID = mSharedPreference.getInt("roomID", 1);
                String deviceID = mSharedPreference.getString("deviceID","");
                RestAdapter adapter=new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
                myAPI api=adapter.create(myAPI.class);
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
            public void failure(RetrofitError error){
                Toast.makeText(getApplicationContext(), "Cannot add device to favorites list!", Toast.LENGTH_LONG).show();

            }
        });


    }
}

