package com.smartx.cookies.smartx.test;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.ViewPlugs;
import com.smartx.cookies.smartx.myAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import models.Plug;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author zamzamy on 5/2/15.
 */
public class ViewPlugsTest extends ActivityInstrumentationTestCase2<ViewPlugs> {

    private ViewPlugs myActivity;
    String ENDPOINT;
    public ViewPlugsTest(){
        super(ViewPlugs.class);
    }

    /**
     * setUp() sets the instance variables to match those of the ViewPlugs Activity it is testing
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        int userID = myActivity.getUserID();
        int roomID = myActivity.getRoomID();
        ENDPOINT = "http://172.20.10.4:3000/";

    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }

    public void testViewPlugsSuccess() throws Exception {
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        api.addPlug("1", "1", "456", "zamzamys tablet", "off", "playstation", new Callback<Plug>() {

                    @Override
                    public void success(Plug plug, Response response) {
                         //Will test on the created plug in the next method along witb the other plug.
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    throw error;
                    }
                });
        api.addPlug("1", "1", "123", "zamzamys phone", "off", "computer", new Callback<Plug>() {
            @Override
            public void success(Plug plug, Response response) {
                api.viewPlugs("1", "1", new Callback<List<Plug>>() {
                    @Override
                    public void success(List<Plug> plugs, Response response) {
                        ArrayList<String> plugIDs = new ArrayList<String>();
                        Iterator<Plug> iterator = plugs.iterator();
                        int i = plugs.size() - 1;
                        while (i >= 0 & iterator.hasNext()) {
                            plugIDs.add(iterator.next().getPlugID());
                            i--;
                        }
                        assertTrue("Plugs contain the first added plug", plugIDs.contains("456"));
                        assertTrue("Plugs contain the first added plug", plugIDs.contains("123"));

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        throw error;
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        }

}
