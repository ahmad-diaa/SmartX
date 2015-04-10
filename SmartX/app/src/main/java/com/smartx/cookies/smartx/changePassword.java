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

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * changePasssword.java
 * Purpose: user can change his password
 *
 * @author Ahmad Abdalraheem
 */

public class changePassword extends Activity {
    Button changePasswordB;
    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;
    String ENDPOINT = "http://192.168.1.3:3000/";
    private String oldPass;
    private String originalPass;
    private String newPass;
    private String confPass;
    private int userID;

    public String getOldPass() {
        return oldPass;
    }

    // @param String for setting the old password
    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getOriginalPass() {
        return originalPass;
    }

    // @param String for setting the original password
    public void setOriginalPass(String originalPass) {
        this.originalPass = originalPass;
    }

    public String getNewPass() {
        return newPass;
    }

    // @param String for setting the new password
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfPass() {
        return confPass;
    }

    // @param String for setting the confirmation password
    public void setConfPass(String confPass) {
        this.confPass = confPass;
    }

    public int getUserID() {
        return userID;
    }

    // @param String for setting the user id
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getENDPOINT() {
        return ENDPOINT;
    }
    // @param String for setting the endpoint

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        changePasswordB = (Button) findViewById(R.id.changePasswordButton);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        originalPass = (mSharedPreference.getString("password", "123456"));

        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

    }

    /**
     * it takes the input from the user to change his password,in case the process of changing passsword succeed it renders a login view, otherwise it toasts an error message,
     *
     * @param v the view of the activity which consists of 3 textfields and a button
     */
    public void changePassword(View v) {
        oldPass = oldPassword.getText().toString();
        newPass = newPassword.getText().toString();
        confPass = confirmPassword.getText().toString();
        if (!newPass.equals(confPass)) {
            Toast.makeText(getApplicationContext(), "Password and confirm password are not the same", Toast.LENGTH_LONG).show();
        } else if (newPass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Please make sure your password at least 6 characters", Toast.LENGTH_LONG).show();
        } else if (!originalPass.equals(oldPass)) {
            Toast.makeText(getApplicationContext(), "Please make sure you entered the correct password", Toast.LENGTH_LONG).show();

        }
        if (newPass.equals(confPass) && originalPass.equals(oldPass)) {
            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
            myAPI api = adapter.create(myAPI.class);
            api.changePassword(userID + "", newPass, new Callback<models.User>() {

                @Override
                public void success(models.User user, Response response) {
                    oldPass = newPass;
                    Toast.makeText(getApplicationContext(), "Your password is successfully changed",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "Make sure you are online", Toast.LENGTH_LONG).show();

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
