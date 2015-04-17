package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.changeInfo;
import com.smartx.cookies.smartx.myAPI;

import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahmad on 08/04/15.
 */
public class changeInfoTest extends ActivityInstrumentationTestCase2<changeInfo> {
    EditText emailTxt;
    EditText phoneTxt;
    Button changeInfoB;
    String email;
    String phone;
    String originalPass;
    int userID;
    String ENDPOINT;
    private changeInfo myInfo;

    public changeInfoTest() {
        super(changeInfo.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myInfo = getActivity();
        changeInfoB = (Button) myInfo.findViewById(R.id.changeInfoB);
        emailTxt = (EditText) myInfo.findViewById(R.id.email);
        phoneTxt = (EditText) myInfo.findViewById(R.id.phone);
        userID = myInfo.getUserID();
        originalPass = myInfo.getOriginalPass();
        ENDPOINT = myInfo.getENDPOINT();
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myInfo);
    }

    public void testchangeInfoSuccess() throws Exception {
        myInfo.runOnUiThread(new Runnable() {
            public void run() {
                emailTxt.setText("email");
                phoneTxt.setText("9565632323");
                email = emailTxt.getText().toString();
                phone = phoneTxt.getText().toString();
            }
        });
        myInfo.changeInfo(myInfo.getWindow().getDecorView());
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.changeInfo(userID + "", email, originalPass, phone, new Callback<User>() {
            @Override
            public void success(User s, Response response) {
                assertEquals("", s.getEmail());
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("email", email);
            }
        });
    }

    public void testFilter() throws Exception {
    }
}