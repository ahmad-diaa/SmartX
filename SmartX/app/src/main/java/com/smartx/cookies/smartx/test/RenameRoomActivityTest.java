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
 * Purpose: tests the RenameRoomActivity
 * Created by maggiemoheb on 4/7/15.
 */

public class RenameRoomActivityTest  extends ActivityInstrumentationTestCase2<RenameRoomActivity> {

    private RenameRoomActivity myActivity;//An instance of the RenameRoomActivity
    private EditText roomName;//An instance of EditText field in the RenameRoomActivity
    private Button rename_button; //An instance of EditText field in the RenameRoomActivity
    private int userID;//The user ID of the RenameRoomActivity
    private int roomID;//The room ID of the RenameRoomActivity
    private String ENDPOINT;//The ENDPOINT of the RenameRoomActivity
    private String errorMessage;//The errorMessage of the RenameRoomActivity

    /**
     * A constructor that matches the super constructor
     */
    public RenameRoomActivityTest() {
        super(RenameRoomActivity.class);
    }

    /**
     * setUp() sets the instance variables to match those of the RenameRoomActivity it is testing
     * @throws Exception
     */
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

    /**
     * tests that myActivity and roomName are passed correctly
     */
    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
        assertNotNull("Text Field is null", roomName);
    }

    /**
     * testRenameRoomSuccess() tests that the name is updated correctly when given the correct parameters
     * @throws Exception
     */
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

    /**
     * testRenameRoomFailure() tests that the room name will not be updated when passed wrong parameters (empty room name)
     * @throws Exception
     */
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

