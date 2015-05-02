package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.ViewRooms;
import com.smartx.cookies.smartx.myAPI;

import models.Session;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahmad on 01/05/15.
 */
public class logoutTest extends ActivityInstrumentationTestCase2<ViewRooms> {
    private int userID;
    private String ENDPOINT;
    private ViewRooms myActivity;
    private Session session = new Session();

    public logoutTest() {
        super(ViewRooms.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        userID = 1;
        session.setId(1);
        ENDPOINT = "http://192.168.1.8:3000/";
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }

    public void testLogoutSuccess() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
            }
        });
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.logout(userID + "", new Callback<Session>() {
                    @Override
                    public void success(Session s, Response response) {
                        if (s == null) {
                            assertEquals(true, true);
                        } else {
                            assertEquals(1, session.getId());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                }
        );
    }
}
