package com.smartx.cookies.smartx;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Purpose: Displays a list of favorite devices.
 *
 * @author ahmaddiaa
 */
public class viewFavoritesActivity extends ListActivity {
    private static int userID;
    private ArrayList<String> roomNameList;
    private ArrayAdapter<String> adapter2;
    private myAPI api;
    private SharedPreferences mSharedPreference;

    public SharedPreferences getmSharedPreference() {
        return mSharedPreference;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        viewFavoritesActivity.userID = userID;
    }

    public myAPI getApi() {
        return api;
    }

    public void setApi(myAPI api) {
        this.api = api;
    }

    public ArrayList<String> getRoomNameList() {
        return roomNameList;
    }

    public String getENDPOINT() {
        return getResources().getString(R.string.ENDPOINT);
    }

    public void setRoomNameList(ArrayList<String> roomNameList) {
        this.roomNameList = roomNameList;
    }

    public ArrayAdapter<String> getAdapter2() {
        return adapter2;
    }

    public void setAdapter2(ArrayAdapter<String> adapter2) {
        this.adapter2 = adapter2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        api = adapter.create(myAPI.class);
        roomNameList = new ArrayList<String>();
        adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, roomNameList);
        setList(userID);
    }

    /**
     * @params UserID user Id of current user
     */
    public void setList(int UserID) {
        api.allDevices(userID + "", new Callback<List<Device>>() {

                    @Override
                    public void success(List<Device> devices, Response response) {
                        Iterator<Device> iterator = devices.iterator();
                        while (iterator.hasNext()) {
                            final Device current = iterator.next();
                            if (current.getFavorite().equals("true")) {
                                int roomid = current.getRoomID();

                                api.getRoom(userID + "", roomid + "", new Callback<String>() {
                                    @Override
                                    public void success(String room, Response response) {
                                        room = room.replace("%20", " ");
                                        roomNameList.add( current.getName()+  "-" + room );
                                        setListAdapter(adapter2);

                                    }
                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, roomNameList);
                            setListAdapter(adapter2);

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
        return true;
    }

    public void viewRoomsClicked(View v) {
        startActivity(new Intent(this, ViewRooms.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}