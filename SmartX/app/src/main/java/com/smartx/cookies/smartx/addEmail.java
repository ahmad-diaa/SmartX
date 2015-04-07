package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import models.*;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class addEmail extends Activity {
    static int userID;
    EditText email;
    String ENDPOINT = "http://192.168.1.106:3000/";
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_email);
        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.passwordEmail);

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID=(mSharedPreference.getInt("userID", 1));
    }

    public void confirmEmail(View v){
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.addEmail(userID +"", email.getText().toString(), password.getText().toString(), new Callback<models.User>() {
            @Override
            public void success(User user, Response response) {
                startActivity(new Intent(getApplicationContext(), ViewRooms.class));
            }


            @Override
            public void failure(RetrofitError error) {
                System.out.print("hi");
                Toast.makeText(getApplicationContext(), "Not a valid email", Toast.LENGTH_LONG).show();


            }
        });
    }

    public void notNow(View v){
        startActivity(new Intent(getApplicationContext(), ViewRooms.class));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_email, menu);
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
