package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.myAPI;
import com.smartx.cookies.smartx.viewFavoritesActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author ahmaddiaa.
 */
public class viewFavoritesTest extends ActivityInstrumentationTestCase2<viewFavoritesActivity> {

    private viewFavoritesActivity myActivity;
    private int userID;
    private String ENDPOINT;
    private static ArrayList<String> favorites = new ArrayList<String>();

    public viewFavoritesTest() {
        super(viewFavoritesActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        userID = 1;
        ENDPOINT = "http://172.20.10.3:3000/";
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }


    /**
     * Compares the expected notes to the notes returned from rails server.
     */
    public void testViewFavorites() throws Exception {

        favorites = new ArrayList<String>();
        final RestAdapter adapter =
                new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.allDevices(userID + "", new Callback<List<Device>>() {

            @Override
            public void success(List<Device> devices, Response response) {
                Iterator<Device> iterator = devices.iterator();
                while (iterator.hasNext()) {
                    final Device current = iterator.next();
                    if (current.getFavorite().equals("true")) {
                        int roomid = current.getRoomID();

                        api.getRoom(userID + "", "1", new Callback<String>() {
                            @Override
                            public void success(String room, Response response) {
                                room = room.replace("%20", " ");
                                favorites.add(current.getName() + "aslks-" + room);
                                myActivity.setList(userID);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                assertEquals(1, 0);
                            }
                        });
                    }

                }

            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
        myActivity.setList(1);
        assertEquals(myActivity.getRoomNameList(), favorites);

    }
}
