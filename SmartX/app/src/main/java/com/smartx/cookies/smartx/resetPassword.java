package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class resetPassword extends Activity {
    String newPass;
    String confPass;
    Button changePasswordB;
    EditText newPassword;
    EditText confirmPassword;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        changePasswordB = (Button) findViewById(R.id.changePasswordButton);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        userID = getIntent().getExtras().getInt("id");
    }

    public void resetPass(View v) {
        newPass = newPassword.getText().toString();
        confPass = confirmPassword.getText().toString();
        if (!newPass.equals(confPass)) {
            Toast.makeText(getApplicationContext(), "Password and confirm password are not the same", Toast.LENGTH_LONG).show();
        } else if (newPass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Please make sure your password at least 6 characters", Toast.LENGTH_LONG).show();
        }
        if (newPass.equals(confPass) && newPass.length() > 5) {
            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
            myAPI api = adapter.create(myAPI.class);
            api.changePassword(userID + "", newPass, new Callback<models.User>() {
                @Override
                public void success(models.User user, Response response) {
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
