package com.smartx.cookies.smartx;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.Iterator;
import java.util.List;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class viewDevices extends ListActivity {

    String ENDPOINT = "http://50.0.30.129:3000/";
    int userID;
    int roomID;
    Button addDevice;
    String[] deviceNames;
    String[] deviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_view_devices);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        addDevice = (Button) findViewById(R.id.addDevice);
        addDevice.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewDevices.this, addDevices.class));
            }
        });


        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewDevices(1 + "", 1 + "", new Callback<List<Device>>() {

            @Override
            public void success(List<Device> devices, Response response) {


                deviceNames = new String[devices.size()];

                Iterator<Device> iterator = devices.iterator();

                int i = devices.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    Device current = iterator.next();
                    deviceNames[i] = current.getName();
                    i--;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNames);
                setListAdapter(adapter);

                startActivity(new Intent(getApplicationContext(), TvClickerActivity.class));

            }

            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(getApplicationContext(), viewDevices.class));

            }
        });


    }

//    public void addDevice (View v) {
//        startActivity(new Intent(
//                this, addDevices.class));
//    }net

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_devices, menu);
        return true;
    }
//    public void onListItemClick(ListView lv ,View view,int position,int imgid) {
//
//        String Slecteditem= (String)getListAdapter().getItem(position);
//        Toast.makeText(this, Slecteditem, Toast.LENGTH_SHORT).show();
//    }


}