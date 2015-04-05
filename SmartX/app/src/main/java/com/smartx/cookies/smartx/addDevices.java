package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import models.Device;
import models.Type;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class addDevices extends Activity implements AdapterView.OnItemSelectedListener {
    int userID;
    int roomID;
    Spinner device_spinner;
    String ENDPOINT = "http://192.168.1.2:3000/";
    EditText device_id;
    Button addDeviceButton;
    myAPI api;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_devices);

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        device_id = (EditText) findViewById(R.id.device_id);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        api = adapter.create(myAPI.class);

        // Spinner element
        device_spinner = (Spinner) findViewById(R.id.device_spinner);
        device_spinner.setOnItemSelectedListener(this);

        final List<String> devices = new ArrayList<String>();
       api.requestTypes(new Callback<List<Type>>() {

            @Override
            public void success(List<Type> types, Response response) {

                Iterator<Type> iterator = types.iterator();
                while (iterator.hasNext()) {
                    String s = iterator.next().getName();
                    devices.add(s);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
        devices.add("None");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, devices);

        // Drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        device_spinner.setAdapter(dataAdapter);

        addDeviceButton = (Button) findViewById(R.id.addDeviceButton);
        addDeviceButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if ((device_spinner.getSelectedItem().toString().equals("None")) ||
                            device_id.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Fill in the Blank spaces", Toast.LENGTH_LONG).show();
                    } else {
                        Device device = new Device(device_spinner.getSelectedItem().toString(), roomID, userID, device_id.getText().toString(), "off");
                        api.addDevice(device.getUserID() + " ", device.getRoomID() + "", device.getName(), device.getDeviceID(), device.getStatus(), new Callback<Device>() {

                            @Override
                            public void success(Device device, Response response) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(addDevices.this);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("deviceID",device_id.getText().toString());
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(), viewDevices.class));
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getApplicationContext(), "Cannot Add A Device!", Toast.LENGTH_LONG).show();
                                throw error;
                            }
                        });
                    }
                }

        });
    }

    public void onNothingSelected(AdapterView arg0) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //noinspection ResourceType
        parent.setSelection(position);
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }
}
