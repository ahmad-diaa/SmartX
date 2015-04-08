package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.RenameRoomActivity;
import com.smartx.cookies.smartx.myAPI;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by maggiemoheb on 4/7/15.
 */
public class RenameRoomActivityTest  extends ActivityInstrumentationTestCase2<RenameRoomActivity> {

    private RenameRoomActivity myActivity;
    private EditText roomName;
    private Button rename_button;
    private int userID;
    private int roomID;
    private String ENDPOINT;
    private String errorMessage;

    public RenameRoomActivityTest() {
        super(RenameRoomActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        roomName = (EditText) myActivity.findViewById(R.id.rename_room);
        rename_button = (Button) myActivity.findViewById(R.id.rename_button);
        userID = myActivity.getUserID();
        roomID = myActivity.getRoomID();
        ENDPOINT = myActivity.getENDPOINT();
        errorMessage =myActivity.getErrorMessage();
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
        assertNotNull("Text Field is null", roomName);
    }

    public void testRenameRoomSuccess() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                roomName.setText("some new name");

            }
        });
        myActivity.renameRoom(myActivity.getWindow().getDecorView());
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.getRoom(userID + "", roomID + "", new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                assertEquals("some new name", s);
                String check = myActivity.getErrorMessage();
                assertEquals("",check);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    public void testRenameRoomFailure() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                roomName.setText("");

            }
        });
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.getRoom(userID + "", roomID + "", new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                myActivity.renameRoom(myActivity.getWindow().getDecorView());
                String check = myActivity.getErrorMessage();
                assertEquals("Room name cannot be blank",check);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}

