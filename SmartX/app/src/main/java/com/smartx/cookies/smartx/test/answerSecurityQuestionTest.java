package com.smartx.cookies.smartx.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.answerSecurityQuestion;
import com.smartx.cookies.smartx.myAPI;

import java.util.List;

import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahmad on 30/04/15.
 */
public class answerSecurityQuestionTest extends ActivityInstrumentationTestCase2<answerSecurityQuestion> {
    private answerSecurityQuestion myActivity;
    private String securityA;
    Intent a;
    EditText answerTxt;
    Button answerB;
    TextView question;
    private int userID=1;
    String answer;
    String ENDPOINT = "http://192.168.1.8:3000/";

    public answerSecurityQuestionTest() {
        super( answerSecurityQuestion.class);
    }
    protected void setUp() throws Exception {
        super.setUp();
        a = new Intent(getInstrumentation().getTargetContext(), answerSecurityQuestion.class);
        a.putExtra("id", 1);
        a.putExtra("securityQuestion", "what?");
        setActivityIntent(a);
        myActivity=getActivity();
        answerTxt = (EditText) myActivity.findViewById(R.id.answerTxt);
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }
    public void testGetQuestion() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                answerTxt.setText("w");
                answer = answerTxt.getText().toString();
            }
        });
        myActivity.submitAnswer(myActivity.getWindow().getDecorView());
        Log.d("Answer ", answer);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.checkUser(userID + "", securityA, new Callback<List<User>>()  {

            @Override
            public void success(List<User> u, Response response) {
                assertEquals(1, u.get(0).getID());
                assertEquals(true,false);
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("email", "eh");
            }
        });
    }

    public void testFilter() throws Exception {
    }
}