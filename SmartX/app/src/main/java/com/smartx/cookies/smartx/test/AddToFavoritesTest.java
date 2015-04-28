package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;
import com.smartx.cookies.smartx.AddToFavorites;
import com.smartx.cookies.smartx.myAPI;
import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Amir on 4/28/2015.
 */
public class AddToFavoritesTest extends ActivityInstrumentationTestCase2<AddToFavorites> {
    private AddToFavorites activity;

    private int userID;
    private int roomID;
    private String deviceID;
    private String ENDPOINT;
    public AddToFavoritesTest() {
        super(AddToFavorites.class);
    }
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        userID = 1;
        roomID = 1;
        deviceID="asd123";
        ENDPOINT = "http://192.168.1.2:3000/";
    }
    public void testPreconditions() {
        assertNotNull("Activity is null", activity);
    }

    public void testFindFavoriteTrue() throws Exception {

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.findFavorite(userID+"",roomID+"",deviceID,new Callback<String>(){
            @Override
            public void success(String favorite,Response response) {
                assertEquals(favorite,"true");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    public void testFindFavoriteFalse() throws Exception {
        deviceID="asd012";
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.findFavorite(userID+"",roomID+"",deviceID,new Callback<String>(){
            @Override
            public void success(String favorite,Response response) {
                assertEquals(favorite,"false");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

public void testAddToFavorites() throws Exception {

    RestAdapter adapter=new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
    myAPI api=adapter.create(myAPI.class);

    api.addToFavorites(userID + "", roomID + "", deviceID, "false", new Callback<Device>() {

        @Override
        public void success(Device device, Response response) {
            assertEquals(device.getFavorite(),"false");
        }

        @Override
        public void failure(RetrofitError error) {

        }
    });
    api.addToFavorites(userID + "", roomID + "", deviceID, "true", new Callback<Device>() {

        @Override
        public void success(Device device, Response response) {
         assertEquals(device.getFavorite(),"true");
        }

        @Override
        public void failure(RetrofitError error) {

        }
    });
}
}
