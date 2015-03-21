package com.smartx.cookies.smartx;

import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class LoginActivity extends ActionBarActivity {
    Button btnLogin;
    Button btn;
    Button btn2;
    String ENDPOINT = "http://172.20.10.4:3000/";
    List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this,About_us.class));
            }

        });

        btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
            }

        });
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new Button.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            System.out.print("yalla bina!!!!");

                                            EditText username = (EditText) findViewById(R.id.txtUserName);
                                            EditText password = (EditText) findViewById(R.id.txtPassword);
                                            String Name = username.getText().toString();
                                            String Pass = password.getText().toString();

                                            System.out.print("Respond1");

                                            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

                                            myAPI api = adapter.create(myAPI.class);
                                            System.out.print("Respond2");
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









                                            // Cursor c = db.showUser(Name);


/*                                            if (c != null) {
                                                if (c.moveToFirst()) {
                                                    String   p = c.getString(c.getColumnIndex("password"));

                                                    if (Pass.equals(p)) {

                                                        // Login
                                                        Intent i = new Intent(v.getContext(),MainActivity.class);
                                                        startActivity(i);
                                                        return;
                                                    } else {

                                                        Toast.makeText(getApplicationContext(), "Incorrect password",
                                                                Toast.LENGTH_LONG).show();
                                                        password.setText("");
                                                        return;
                                                    }



                                                }
                                            }
                                            Toast.makeText(getApplicationContext(), "Username does not exist",
                                                    Toast.LENGTH_LONG).show();
                                            c.close();
                                        }
*/


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

    private void requestData(String uri){


        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

        myAPI api = adapter.create(myAPI.class);



//     //   api.getFeed(new Callback<List<User>>() {
//
//
//            @Override
//            public void success(List<User> users, Response response) {
//
//                userList = users;
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });

}

}