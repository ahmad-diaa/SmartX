package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Callable;

import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class changeInfo extends Activity {
    EditText emailtxt;
    EditText phonetxt;
    Button changeInfoB;
    String email;
    String phone;
    String originalPass;
    int userID;
    String test;
    String ENDPOINT = "http://192.168.1.3:3000/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        changeInfoB = (Button) findViewById(R.id.changeInfoB);
        emailtxt = (EditText) findViewById(R.id.email);
        phonetxt = (EditText) findViewById(R.id.phone);
        userID=(mSharedPreference.getInt("userID", 1));
        originalPass= (mSharedPreference.getString("password", "123456"));

    }
   public void changeInfo (View v) {
       email = emailtxt.getText().toString();
       phone = phonetxt.getText().toString();
       final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

       if (email.equals(null)) {
           email = (mSharedPreference.getString("email", ""));
       }

       if (phone.equals(null)){
           phone=(mSharedPreference.getString("phone", ""));
       }

       RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
       myAPI api = adapter.create(myAPI.class);
       api.changeInfo(userID + "", email,originalPass,phone, new Callback<User>() {
           @Override
           public void success(User user, Response response) {
               SharedPreferences.Editor editor = mSharedPreference.edit();
               editor.putString("email", email);
               editor.putString("phone", phone);
               editor.commit();
               Toast.makeText(getApplicationContext(),"Your information Successfully updated", Toast.LENGTH_LONG).show();
               startActivity(new Intent(getApplicationContext(), ViewRooms.class));

           }

           @Override
           public void failure(RetrofitError error) {

               Toast.makeText(getApplicationContext(),"Please make sure you are connected to the internet", Toast.LENGTH_LONG).show();
           }
       });
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
