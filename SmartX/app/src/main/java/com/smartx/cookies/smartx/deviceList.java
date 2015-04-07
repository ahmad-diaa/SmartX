package com.smartx.cookies.smartx;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class deviceList extends ListActivity {

    String ENDPOINT = "http://192.168.1.106:3000/";
    static int userID;
    String type;
    static ArrayList<String> deviceNames;
    static ArrayList<Integer> deviceIds;
    String[] deviceIDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        type = mSharedPreference.getString("deviceType", "TV");
        userID = (mSharedPreference.getInt("userID", 1));
        deviceNames = new ArrayList<String>();
        deviceIds = new ArrayList<Integer>();
            final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        final myAPI api = adapter.create(myAPI.class);
        final ArrayList<String> roomNameList = new ArrayList<String>();

        api.allDevices(userID +"",new Callback<List<Device>>() {

                           @Override
                           public void success(List<Device> devices, Response response) {
                               Iterator<Device> iterator = devices.iterator();
                               Iterator<Device> iterator2 = devices.iterator();

                               while (iterator.hasNext()) {
                                   if (type.equalsIgnoreCase(iterator2.next().getName())) {
                                       api.getRoom(userID + "", iterator.next().getRoomID()+"", new Callback<Room>() {
                                                   @Override
                                                   public void success(Room room, Response response) {
                                                       roomNameList.add(room.get_roomName());
                                                       ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, roomNameList);
                                                       setListAdapter(adapter2);

                                                   }

                                                   @Override
                                                   public void failure(RetrofitError error) {
                                                        //add toast
                                                   }
                                               });
                               }
                                   else {
                                       iterator.next();
                                   }
                               }
                           }

                           @Override
                           public void failure(RetrofitError error) {
                               //add toast
                           }


                       }
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, roomNameList);
        setListAdapter(adapter2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
        return true;

    }
    public void viewRoomsClicked(View v){
        startActivity(new Intent(this,ViewRooms.class));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}