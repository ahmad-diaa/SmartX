package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.smartx.cookies.smartx.LoginActivity;
import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.myAPI;

import models.securityQuestion;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Purpose:This class is to test getQuestion method.
 *
 * @author Ahmad Abdalraheem
 */
public class getQuestionTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity myActivity;
    private String securityA;
    EditText txtUserName;
    String name;
    boolean flag;
    String ENDPOINT = "http://192.168.1.8:3000/";

    public getQuestionTest() {
        super(LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        txtUserName = (EditText) myActivity.findViewById(R.id.txtUserName);
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }

    public void testGetQuestion() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                txtUserName.setText("ahaa");
                name = txtUserName.getText().toString();
            }
        });
        myActivity.getSecurityQuestion(myActivity.getWindow().getDecorView());
        Log.d("Username ", name);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.getSecurityQuestion(name, new Callback<securityQuestion>() {

            @Override
            public void success(securityQuestion securityQuestion, Response response) {
                flag = true;
                assertEquals("what?", securityQuestion.getSecurityQ());
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals(1, 2);
            }
        });
    }

    public void testFilter() throws Exception {
    }
}