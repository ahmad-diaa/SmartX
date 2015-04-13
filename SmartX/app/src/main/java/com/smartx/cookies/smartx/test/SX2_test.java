package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.changePassword;
import com.smartx.cookies.smartx.myAPI;

import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahmad on 08/04/15.
 */
public class SX2_test extends ActivityInstrumentationTestCase2<changePassword> {

    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;
    Button changePassB;
    String confPass;
    String oldPass;
    String newPass;
    int userID;
    String ENDPOINT;
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
        userID = changePass.getUserID();

        ENDPOINT = changePass.getENDPOINT();

    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", changePass);
    }

    public void testChangePasswordSuccess() throws Exception {
        changePass.runOnUiThread(new Runnable() {
            public void run() {
                changePass.setOriginalPass("123456");
                oldPassword.setText("123456");
                confirmPassword.setText("asdfgh");
                newPassword.setText("asdfgh");
                newPass = newPassword.getText().toString();
                confPass = confirmPassword.getText().toString();
                oldPass = oldPassword.getText().toString();
                assertEquals("123456", oldPass);
                assertEquals(changePass.getOriginalPass(), oldPass);
                assertEquals("asdfgh", newPass);
                assertEquals(newPass, confPass);


            }
        });

        changePass.changePassword(changePass.getWindow().getDecorView());
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.changePassword(userID + "", newPass, new Callback<User>() {

            @Override
            public void success(User s, Response response) {
                assertEquals("asdfgh", changePass.getOldPass());
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals("123456", changePass.getOriginalPass());


            }
        });
    }

    public void testFilter() throws Exception {


    }


}
