package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.POST;
import retrofit.http.Path;


public class AddDevices extends Activity implements AdapterView.OnItemSelectedListener {
    int userID;
    int roomID;
    Spinner device_spinner;
    List<String> brands;
    Spinner brand_spinner;
    ArrayAdapter<String> dataAdapter2;
    String ENDPOINT = "http://192.168.1.13:3000/";
    int brand_spinner_id = 2131296325;
    int device_spinner_id = 2131296323;

    EditText device_name;
    Button addDeviceButton;
    myAPI api;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_devices);

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        device_name = (EditText) findViewById(R.id.device_name);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        api = adapter.create(myAPI.class);

        // Spinner element
        device_spinner = (Spinner) findViewById(R.id.device_spinner);

        device_spinner.setOnItemSelectedListener(this);

        List<String> devices = new ArrayList<String>();
        devices.add("None");
        devices.add("TV");
        devices.add("Fridge");
        devices.add("Air Conditioner");
        devices.add("Plug");
        devices.add("Lamp");
        devices.add("Set-top Box");
        devices.add("Projector");
        devices.add("DVD Player");
        devices.add("Blu-ray Player");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, devices);
        device_spinner.setSelection(0);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        device_spinner.setAdapter(dataAdapter);

        ////////////////////////////////////NEW SPINNER/////////////////////////////////////////////////

        brand_spinner = (Spinner) findViewById(R.id.brand_spinner);
        brand_spinner.setOnItemSelectedListener(this);

        brands = new ArrayList<String>();

        dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brands);


        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brand_spinner.setEnabled(false);
        brand_spinner.setClickable(false);
        brand_spinner.setAdapter(dataAdapter2);

        addDeviceButton = (Button) findViewById(R.id.addDeviceButton);
        addDeviceButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((device_spinner.getSelectedItem().toString().equals("None")) ||
                        (brand_spinner.getSelectedItem().toString().equals("None"))
                        ) {
                    Toast.makeText(getApplicationContext(), "Please Fill in the Blank spaces", Toast.LENGTH_LONG).show();
                } else {
                    Device device = new Device(device_spinner.getSelectedItem().toString(), device_name.getText().toString(), brand_spinner.getSelectedItem().toString(), roomID, userID);

                    api.addDevice(device.getName(), device.getUserID() + "", device.getRoomID() + "", device.getType(), device.getBrand(), new Callback<Device>() {
                        @Override
                        public void success(Device device, Response response) {
                            startActivity(new Intent(getApplicationContext(), About_us.class));


                        }

                        @Override
                        public void failure(RetrofitError error) {
                            throw error;

                        }


                    });


                }
            }
        });
    }

    @Override
    public void onItemSelected(final AdapterView parent, View view, int position,
                               long id) {


        //noinspection ResourceType
        if (parent.getId() == device_spinner_id) {



            if (position > 0) {
                parent.setSelection(position);
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();




                    dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brands);
                    api.requestBrands(item, new Callback<List<Type>>() {
                        @Override
                        public void success(List<Type> types, Response response) {
Log.i("In succes part", " ");
                            brands.add("None");


                            Iterator<Type> iterator = types.iterator();

                            while (iterator.hasNext()) {


                                String s = iterator.next().getBrand();
                                brands.add(s);
                                Log.i("Brand name", s);


                            }



                            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            brand_spinner.setEnabled(true);
                            brand_spinner.setClickable(true);
                            brand_spinner.setAdapter(dataAdapter2);

                        }


                        @Override
                        public void failure(RetrofitError error) {
                            throw error;


                        }

                    });
               if (parent.getSelectedItem().toString().equals("None")) {
                    brands.clear();
                   dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brands);
                   dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   brand_spinner.setEnabled(false);
                   brand_spinner.setClickable(false);
                   brand_spinner.setAdapter(dataAdapter2);

                }
            } else {


            }
            brands.clear();
        }

    }



//            public void addDeviceButton(View v) {
//                addDeviceButton = (Button) findViewById(R.id.addDeviceButton);
//
//                Device device = new Device(device_spinner.getSelectedItem().toString(), device_name.getText().toString(), brand_spinner.getSelectedItem().toString(), roomID, userID);
//
//                //myAPI api = adapter.create(myAPI.class);
//                // System.out.print("Respond2");
//                //  api.addRoom(roomName.getText().toString(), "soora", userID +"", new Callback<Room> () {
//                // api.addRoom("12", room.get_roomName(),"Photo" ,new Callback<Room>(){
//                api.addDevice(device.getUserID() + "", device.getRoomID() + "", device.getName(), device.getUserID() + "", device.getRoomID() + "", device.getType(), device.getBrand(), new Callback<Device>() {
//
//                    @Override
//                    public void success(Device device, Response response) {
//                        startActivity(new Intent(getApplicationContext(), About_us.class));
//
//
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        throw error;
//                        //Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
//                       // startActivity(new Intent(getApplicationContext(), About_us.class));
//
//
////
//                    }
//                });
//            }

            public void onNothingSelected(AdapterView arg0) {

            }


        }
