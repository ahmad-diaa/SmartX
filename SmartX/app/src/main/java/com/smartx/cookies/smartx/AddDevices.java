package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.POST;


public class AddDevices extends Activity implements AdapterView.OnItemSelectedListener  {

    List<String> brands;
    Spinner brand_spinner;
    ArrayAdapter<String> dataAdapter2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_devices);

        // Spinner element
        Spinner device_spinner = (Spinner) findViewById(R.id.device_spinner);

        device_spinner.setOnItemSelectedListener(this);

        List<String> devices = new ArrayList<String>();
        devices.add("None");
        devices.add("TV");
        devices.add("Fridge");
        devices.add("Air Conditioner");
        devices.add("Plug");
        devices.add("Lamp");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, devices);
        device_spinner.setSelection(0);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        device_spinner.setAdapter(dataAdapter);

        ////////////////////////////////////NEW SPINNER/////////////////////////////////////////////////

        brand_spinner = (Spinner) findViewById(R.id.brand_spinner);        //List<String> type = ale gay mn l db
        brand_spinner.setOnItemSelectedListener(this);

        brands = new ArrayList<String>();

        dataAdapter2 = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, brands);


        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brand_spinner.setEnabled(false);
        brand_spinner.setClickable(false);
        brand_spinner.setAdapter(dataAdapter2);





    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        if (position > 0) {
            String item = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            brand_spinner.setEnabled(true);
            brand_spinner.setClickable(true);
            brand_spinner.setAdapter(dataAdapter2);

            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

            myAPI api = adapter.create(myAPI.class);
            api.login(Name, Pass, new Callback<User>() {


                @Override
                public void success(User user, Response response) {
                    startActivity(new Intent(LoginActivity.this,About_us.class));
                }

                @Override
                public void failure(RetrofitError error) {
                    throw error;

                }
            });


        } else {
           // Toast.makeText(parent.getContext(), "Select Device", Toast.LENGTH_LONG).show();


        }
    }
    public void onNothingSelected(AdapterView arg0) {
        // TODO Auto-generated method stub

    }

}




