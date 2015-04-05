package com.smartx.cookies.smartx;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.*;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class changePassword extends ActionBarActivity {
    String oldPass;
    String originalPass;
    String newPass;
    String confPass;
    Button changePasswordB;
    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;
    int userID;
    String ENDPOINT = "197.161.15.4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        changePasswordB = (Button) findViewById(R.id.changePasswordButton);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID=(mSharedPreference.getInt("userID", 1));
        originalPass= (mSharedPreference.getString("password", "1"));
        oldPassword = (EditText) findViewById(R.id.passwordOld);
        newPassword = (EditText) findViewById(R.id.passwordNew);
        confirmPassword = (EditText) findViewById(R.id.passwordConfirm);

    }


    public void changePassword (View v) {
        oldPass = oldPassword.getText().toString();
        newPass = newPassword.getText().toString();
        confPass = confirmPassword.getText().toString();
        if (newPass.equals(confPass) && originalPass.equals(oldPass)) {
            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
            myAPI api = adapter.create(myAPI.class);
            api.changePassword(userID +"",newPass, new Callback<models.User>() {

                @Override
                public void success(models.User user, Response response) {
                    Toast.makeText(getApplicationContext(), "Your password is successfully changed",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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
