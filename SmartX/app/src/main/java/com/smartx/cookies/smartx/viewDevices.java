package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.ListActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import models.Device;
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class viewDevices extends ListActivity{

    String ENDPOINT = "http://192.168.1.2:3000/";
    int userID;
    int roomID;
    Button addDevice;
    String [] deviceNames;

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

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewDevices(userID +"",roomID + "", new Callback<List<Device>>() {

            @Override
            public void success(List<Device> devices, Response response) {
                deviceNames = new String[devices.size()];
                Iterator<Device> iterator = devices.iterator();
                int i = devices.size() - 1;
                while(i>= 0 & iterator.hasNext()){
                    deviceNames[i] = iterator.next().getName();
                    i--;
                }
                ArrayAdapter <String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNames);
                setListAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }


    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String device = o.toString();
        Toast.makeText(getApplicationContext(), device, Toast.LENGTH_LONG).show();
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = ADAPTER.create(myAPI.class);
        api.findDevice(userID + "",roomID+"",device, new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                SharedPreferences prefs =
                        PreferenceManager.getDefaultSharedPreferences(viewDevices.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("deviceID", devices.get(0).getId());
                editor.commit();
                startActivity(new Intent(viewDevices.this, About_us.class));
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
}