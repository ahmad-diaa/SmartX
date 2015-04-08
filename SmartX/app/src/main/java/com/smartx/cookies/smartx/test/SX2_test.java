package com.smartx.cookies.smartx.test;

import com.smartx.cookies.smartx.changePassword;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.myAPI;

import java.util.prefs.Preferences;

import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
* Created by ahmad on 08/04/15.
*/
public class SX2_test extends ActivityInstrumentationTestCase2<changePassword> {

    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;
    Button changePassB;
    String oldPass;
    String originalPass;
    String newPass;
    String confPass;
    int userID;
    String ENDPOINT ;
    changePassword changePass;

    public SX2_test() {
        super(changePassword.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        changePass = getActivity();

        changePassB = (Button) changePass.findViewById(R.id.changePasswordB);
        oldPassword = (EditText) changePass.findViewById(R.id.oldPassword);
        newPassword = (EditText) changePass.findViewById(R.id.newPassword);
        confirmPassword = (EditText) changePass.findViewById(R.id.confirmPassword);

        userID=changePass.getUserID();
        originalPass= changePass.getOldPass();

        ENDPOINT = changePass.getENDPOINT();

    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", changePass);
    }
    public void testChangePasswordSuccess() throws Exception {
        changePass.runOnUiThread(new Runnable() {
            public void run() {
               oldPassword.setText("123465");
//                confirmPassword.setText("654321");
                newPassword.setText("654321");
                newPass = newPassword.getText().toString();

            }
        });
        changePass.changePassword(changePass.getWindow().getDecorView());
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.changePassword(userID + "",newPass, new Callback<User>() {
            @Override
            public void success(User s, Response response) {
                assertEquals(newPass, changePass.getOldPass());

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
//    public void testChangePasswordFailure() throws Exception {
//        changePass.runOnUiThread(new Runnable() {
//            public void run() {
//                newPassword.setText("");
//            }
//        });
//        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
//        myAPI api = adapter.create(myAPI.class);
//        api.changePassword(userID + "",newPass, new Callback<User>() {
//            @Override
//            public void success(User s, Response response) {
//                changePass.changePassword(changePass.getWindow().getDecorView());
//
//                assertEquals("",changePass.getOldPass());
//            }
//
//
//
//            @Override
//            public void failure(RetrofitError error) {
//            }
//        });
//    }

    public void testFilter() throws Exception {



    }



}
