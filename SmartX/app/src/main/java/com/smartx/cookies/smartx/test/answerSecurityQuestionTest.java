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
    String ENDPOINT = "http://192.168.1.4:3000/";

    public answerSecurityQuestionTest() {
        super( answerSecurityQuestion.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        a = new Intent(getInstrumentation().getTargetContext(), answerSecurityQuestion.class);
        a.putExtra("id", 1);
        a.putExtra("securityQuestion", "what's my age?");
        setActivityIntent(a);
        myActivity=getActivity();
        question = (TextView) myActivity.findViewById(R.id.question);
        answerTxt = (EditText) myActivity.findViewById(R.id.answerTxt);
        answerB = (Button) myActivity.findViewById(R.id.answerB);
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }

    public void testSubmitAnswerSuccess() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                Log.d("ana weselt", "1");

                answerTxt.setText("bababa");
                 myActivity.setAnswer(answerTxt.getText().toString());
                 securityA = myActivity.getAnswer();
                Log.d("ana weselt15", securityA);
            }
           });
        Log.d("anahey", "2");

        myActivity.submitAnswer(myActivity.getWindow().getDecorView());
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        assertEquals(1,userID);
        Log.d("anaDsy", "3");
        api.checkUser(5 +"", "tee", new Callback<List<User>>() {
            @Override
            public void success(List<User> user, Response response) {
                Log.d("ana weselt", "7amdellah 3al salama");
                 assertEquals(true,false);
                 assertEquals("1", user.get(0).getID());
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals(true,false);

            }
        });
    }
}
