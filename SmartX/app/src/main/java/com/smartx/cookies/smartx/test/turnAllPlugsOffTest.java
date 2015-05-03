package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.ViewRooms;
import com.smartx.cookies.smartx.myAPI;

import java.util.List;

import models.Plug;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dalia on 5/1/15.
 */
public class turnAllPlugsOffTest extends ActivityInstrumentationTestCase2<ViewRooms> {
    private ViewRooms myActivity;

    private String ENDPOINT;
    RestAdapter adapter;
    myAPI api;

    public turnAllPlugsOffTest() {
        super(ViewRooms.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        ENDPOINT = "http://192.168.1.106:3000/";
        adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        api = adapter.create(myAPI.class);
    }

    public void testTurnOff(){
        myActivity.turnThemOff();
        api.getPlugs("1","1", new Callback<List<Plug>>() {
            @Override
            public void success(List<Plug> plugs, Response response) {
                for (int i=0; i<plugs.size();i++){
                    assertEquals("off",plugs.get(i).getStatus());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals(0,1);
            }
        });
    }
}
