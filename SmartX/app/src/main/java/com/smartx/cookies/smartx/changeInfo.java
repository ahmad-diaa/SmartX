package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * changeInfo.java
 * Purpose: user can edit his contact information (email and phone number)
 *
 * @author Ahmad Abdalraheem
 */
public class changeInfo extends Activity {
    EditText emailTxt;
    EditText phoneTxt;
    Button changeInfoB;
    private String email;
    private String phone;
    private String originalPass;
    private int userID;

    public String getEmail() {
        return email;
    }
// @param String for setting the email

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
// @param String for setting the phone number

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // @param String for setting the original password
    public String getOriginalPass() {
        return originalPass;
    }

    public void setOriginalPass(String originalPass) {
        this.originalPass = originalPass;
    }

    public int getUserID() {
        return userID;
    }
    // @param String for setting the user id

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getENDPOINT() {
        return getResources().getString(R.string.ENDPOINT);
    }
    // @param String for setting the endpoint


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        changeInfoB = (Button) findViewById(R.id.changeInfoB);
        emailTxt = (EditText) findViewById(R.id.email);
        emailTxt.setText(mSharedPreference.getString("email", ""));
        phoneTxt = (EditText) findViewById(R.id.phone);
        phoneTxt.setText(mSharedPreference.getString("phone", ""));
        userID = (mSharedPreference.getInt("userID", 1));
        originalPass = (mSharedPreference.getString("password", "123456"));
    }

    /**
     * it takes the input from the user to change his information,it toast whether the information is updated or not,
     *
     * @param v the view of the activity.
     */
    public void changeInfo(View v) {
        email = emailTxt.getText().toString();
        phone = phoneTxt.getText().toString();
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (email.equals(null)) {
            email = (mSharedPreference.getString("email", ""));
        }
        if (phone.equals(null)) {
            phone = (mSharedPreference.getString("phone", ""));
        }
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);

        api.changeInfo(userID + "", email, originalPass, phone, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.putString("email", email);
                editor.putString("phone", phone);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Your information Successfully updated", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ViewRooms.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
