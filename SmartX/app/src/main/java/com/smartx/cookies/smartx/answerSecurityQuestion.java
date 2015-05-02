package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * answerSecurityQuestion.java
 * Purpose:It loads user's security question, if he answer it right it redirect him to change password.
 *
 * @author Ahmad Abdalraheem
 */

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
    }

    /**
     * it takes the answer od the security question of the user, if it's right it redirect him to change password other wise toast and error message,
     *
     * @param v the view of the activity.
     */
    public void submitAnswer(View v) {
        userID = getIntent().getExtras().getInt("id");
        securityA = answerTxt.getText().toString();
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.checkUser(userID + "", securityA, new Callback<List<User>>() {
            @Override
            public void success(List<User> user, Response response) {
                if (user.get(0).getGetSecurityA().equals(securityA)) {
                    Intent rs = new Intent(getApplicationContext(), changePassword.class);
                    rs.putExtra("id", userID);
                    rs.putExtra("flag", 1);
                    startActivity(rs);
                } else {
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Please check your answer", Toast.LENGTH_LONG).show();
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

    /**
     * get id of the user.
     *
     * @return primary key of the user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * set id of the user.
     *
     * @param userID the primary key of the user.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * get security answer of the user.
     *
     * @return the answer of the security question of the user.
     */

    public String getAnswer() {
        return securityA;
    }

    /**
     * set security answer of the user.
     *
     * @param a the new answer for the security question.
     */
    public void setAnswer(String a) {
        this.securityA = a;
    }
}
