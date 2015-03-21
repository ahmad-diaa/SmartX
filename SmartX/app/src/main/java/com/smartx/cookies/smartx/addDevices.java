package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class addDevices extends ActionBarActivity {


    public class AddDevices extends Activity implements AdapterView.OnItemSelectedListener {
        int userID;
        int roomID;
        Spinner device_spinner;
        List<String> brands;
        Spinner brand_spinner;
        ArrayAdapter<String> dataAdapter2;
        String ENDPOINT = "http://172.20.10.4:3000/";
        AdapterView parent;
        EditText device_name;
        Button addDeviceButton;
        myAPI api;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_devices);

            final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            userID = (mSharedPreference.getInt("userID", 1));
            roomID = (mSharedPreference.getInt("roomID", 1));



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

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, devices);
            device_spinner.setSelection(0);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner


            device_spinner.setAdapter(dataAdapter);

            ////////////////////////////////////NEW SPINNER/////////////////////////////////////////////////

            brand_spinner = (Spinner) findViewById(R.id.brand_spinner);        //List<String> type = ale gay mn l db
            brand_spinner.setOnItemSelectedListener(this);

            brands = new ArrayList<String>();

            dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brands);


            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_spinner.setEnabled(false);
            brand_spinner.setClickable(false);
            brand_spinner.setAdapter(dataAdapter2);




        }

        @Override
        public void onItemSelected(final AdapterView parent, View view, int position, long id) {
            // Log.i("First Line in Method: ", "________" + parent.toString());
            this.parent = parent;
            if (position > 0) {
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


//            if (parent.getSelectedItem().toString().equals("TV") || parent.getSelectedItem().toString().equals("Lamp") || parent.getSelectedItem().toString().equals("Fridge")
                //                  || parent.getSelectedItem().toString().equals("Air Conditioner") || parent.getSelectedItem().toString().equals("Plug")) {
                dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brands);


                api.requestBrands(item, new Callback<List<Type>>() {
                    @Override
                    public void success(List<Type> types, Response response) {

                        //brands.add("None");
                        //brand_spinner.setSelection(0);

                        Iterator<Type> iterator = types.iterator();

                        while (iterator.hasNext()) {


                            String s = iterator.next().getBrand();
                            brands.add(s);


                        }
                        //brands.add("Other");

                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        brand_spinner.setEnabled(true);
                        brand_spinner.setClickable(true);
                        brand_spinner.setAdapter(dataAdapter2);
                    }


                    @Override
                    public void failure(RetrofitError error) {
                        throw error;
                        // Toast.makeText(parent.getContext(), "An error has occured", Toast.LENGTH_LONG).show();
                        // startActivity(new Intent(getApplicationContext(), About_us.class));

                    }

                });
            } //else if(parent.getSelectedItem().toString().equals("None")){

            //}
            //else {

            //}
            //}

            else{
                // Toast.makeText(parent.getContext(), "Select Device", Toast.LENGTH_LONG).show();


            }
        }




        public void addDeviceButton(View v) {
            addDeviceButton = (Button) v;

            Device device = new Device(device_spinner.getSelectedItem().toString(), device_name.getText().toString(), brand_spinner.getSelectedItem().toString(), roomID, userID);

            //myAPI api = adapter.create(myAPI.class);
            // System.out.print("Respond2");
            //  api.addRoom(roomName.getText().toString(), "soora", userID +"", new Callback<Room> () {
            // api.addRoom("12", room.get_roomName(),"Photo" ,new Callback<Room>(){
            api.addDevice(device.getUserID() + "", device.getRoomID() + "", device.getName(), device.getUserID() + "", device.getRoomID() + "", device.getType(), device.getBrand(), new Callback<Device>() {

                @Override
                public void success(Device device, Response response) {
                    startActivity(new Intent(getApplicationContext(), About_us.class));


                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(parent.getContext(), "An error has occured", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), About_us.class));



                }
            });
        }

        public void onNothingSelected(AdapterView arg0) {
            addDeviceButton.setEnabled(false);
        }



    }
}
