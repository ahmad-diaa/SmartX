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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import models.*;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class answerSecurityQuestion extends Activity {
    private String securityA;
    EditText answerTxt;
    Button answerB;
    TextView question;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_security_question);
        answerB = (Button) findViewById(R.id.answerB);
        answerTxt = (EditText) findViewById(R.id.answerTxt);
        question = (TextView) findViewById(R.id.question);
        question.setText(getIntent().getExtras().getString("securityQuestion"));
        userID = getIntent().getExtras().getInt("id");
    }


    public void submitAnswer(View v) {
        securityA = answerTxt.getText().toString();
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);

        api.checkUser(userID + "", securityA, new Callback<List<User>>()  {
            @Override
            public void success(List<User> user, Response response) {

                if (!user.isEmpty()) {
                    Intent rs = new Intent(getApplicationContext(), changePassword.class);
                    rs.putExtra("id", userID);
                    rs.putExtra("flag",1);
                    startActivity(rs);
                } else {
                    Toast.makeText(getApplicationContext(), "Answer is Incorrect", Toast.LENGTH_LONG);
                }
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
        getMenuInflater().inflate(R.menu.menu_answer_security_question, menu);
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
