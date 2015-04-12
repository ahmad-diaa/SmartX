package com.smartx.cookies.smartx;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Iterator;
import java.util.List;
import android.app.ListActivity;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class viewDevices extends ListActivity{


    int userID;
    int roomID;
    String roomName;
    Button addDevice;
    ArrayList<String> deviceNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_devices);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID=(mSharedPreference.getInt("userID",1));
        roomID=(mSharedPreference.getInt("roomID", 1));
        addDevice = (Button) findViewById(R.id.addDevice);
        addDevice.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
        startActivity(new Intent(viewDevices.this, addDevices.class));
            }
        });

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.getRoom(userID+"",roomID+"",new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                roomName = s;
                TextView roomText = (TextView)findViewById(R.id.textView6);
                roomText.setText(roomName);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(roomID + " " , error.getMessage());
                Toast.makeText(getApplicationContext(), "Something went wrong with room name, please try again", Toast.LENGTH_LONG).show();
            }
        });
        api.viewDevices(userID + "", roomID + "", new Callback<List<Device>>() {

            @Override
            public void success(List<Device> devices, Response response) {
                deviceNames = new ArrayList<String>();
                Iterator<Device> iterator = devices.iterator();
                int i = devices.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    deviceNames.add(iterator.next().getName());
                    i--;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNames);
                setListAdapter(adapter);


        }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_devices, menu);
        return true;
    }
    public void renameRoom(View v) {
        startActivity(new Intent(this, renameRoomActivity.class));
    }
}