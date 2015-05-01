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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class viewDevices extends ListActivity {

    int userID;
    int roomID;
    String roomName;
    Button addDevice;
    CustomListDevice adapter2;
    EditText editSearch;
    ArrayList<String> deviceNames;
    Button addPlug;
    Button viewPlugs;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_devices);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        addPlug = (Button) findViewById(R.id.addplug);
        addPlug.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewDevices.this, AddPlug.class));
            }
        });
        viewPlugs = (Button) findViewById(R.id.button5);
        viewPlugs.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewDevices.this, ViewPlugs.class));
            }
        });
        addDevice = (Button) findViewById(R.id.addDevice);
        addDevice.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewDevices.this, addDevices.class));
            }
        });

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        Log.d(userID + "", roomID + "");
        api.getRoom(userID + "", roomID + "", new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                roomName = s.replace("%20", " ");
                TextView roomText = (TextView) findViewById(R.id.textView6);
                roomText.setText(roomName);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR ", error.getMessage());
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
                adapter2 = new CustomListDevice(viewDevices.this, deviceNames);
                setListAdapter(adapter2);
                editSearch = (EditText) findViewById(R.id.search);


                // Capture Text in EditText

                editSearch.addTextChangedListener(new

                                                          TextWatcher() {

                                                              @Override
                                                              public void afterTextChanged (Editable arg0){
                                                                  // TODO Auto-generated method stub
                                                                  String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                                                                  adapter2.filter2(text);
                                                              }

                                                              @Override
                                                              public void beforeTextChanged (CharSequence arg0,int arg1,
                                                                                             int arg2, int arg3){
                                                                  // TODO Auto-generated method stub
                                                              }

                                                              @Override
                                                              public void onTextChanged (CharSequence arg0,int arg1, int arg2,
                                                                                         int arg3){
                                                                  // TODO Auto-generated method stub
                                                              }
                                                          }

                );

            }

            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(getApplicationContext(), viewDevices.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_devices, menu);
        return true;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String device = o.toString();
        Toast.makeText(getApplicationContext(), device, Toast.LENGTH_LONG).show();
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = ADAPTER.create(myAPI.class);
        api.findDevice(userID + "", roomID + "", device, new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                SharedPreferences prefs =
                        PreferenceManager.getDefaultSharedPreferences(viewDevices.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("deviceID", devices.get(0).getDeviceID() + "");
                editor.commit();
                startActivity(new Intent(getApplicationContext(), TvClickerActivity.class));

            }


            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }
}