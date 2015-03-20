package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends ActionBarActivity {
    Button btnLogin;
    Button btn;
    Button btn2;
    String ENDPOINT = "http://84.233.102.39:3000/";
    List<User> userList;
    SharedPreferences Data;
    public static final String sharedPrefs = "MySharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Data = getSharedPreferences(sharedPrefs, 0);


        setContentView(R.layout.activity_login);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, About_us.class));
            }

        });

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, device.class));
            }

        });
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText username = (EditText) findViewById(R.id.txtUserName);
                EditText password = (EditText) findViewById(R.id.txtPassword);
                String Name = username.getText().toString();
                String Pass = password.getText().toString();


                RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

                myAPI api = adapter.create(myAPI.class);
                api.login(Name, Pass, new Callback<Session>() {
                    @Override
                    public void success(Session session, Response response) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("userID", session.getId());
                        editor.commit();
                        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
                        myAPI api = adapter.create(myAPI.class);

                        api.getFeed(session.getId(), new Callback<models.User>() {
                            @Override
                            public void success(models.User user, Response response) {
                                String Name = user.getName();
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("userID", user.getID());
                                editor.putString("Name", Name);
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(),ViewRooms.class));
                            }

                            @Override
                            public void failure(RetrofitError error) {
//                                Log.i("ahmad  :", error.getMessage());
//                                if(error.getMessage().equals("401 Unauthorized"))
//                                {
//                                    Toast.makeText(getApplicationContext(),"Wrong Username/Password",Toast.LENGTH_LONG);
//                                }
                                Toast.makeText(getApplicationContext(),"Make sure you are online",Toast.LENGTH_LONG).show();

//
                            }
                        });

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("ahmad", error.getMessage());
                        if(error.getMessage().contains("401 Unauthorized"))
                        {
                            Toast.makeText(getApplicationContext(),"Wrong Username/Password",Toast.LENGTH_LONG).show();
                        }else
                        {
                            Toast.makeText(getApplicationContext(),"Make sure you are online.\nIf this problem proceeds, contact us.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void requestData(String uri) {


        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

        myAPI api = adapter.create(myAPI.class);


    }

}