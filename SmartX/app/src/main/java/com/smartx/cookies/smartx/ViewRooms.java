package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;
import android.widget.Toast;
import java.util.Iterator;
import java.util.List;
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewRooms extends ListActivity {

    String ENDPOINT = "http://192.168.1.2:3000/";
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewRooms(userID + "", new Callback<List<Room>>() {

            @Override
            public void success(List<Room> rooms, Response response) {
                String[] roomNames = new String[rooms.size()];
                final Integer[] roomImages = new Integer[rooms.size()];
                Iterator<Room> iterator = rooms.iterator();
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames[i] = iterator.next().getName();
                    //      roomImages[i] = Integer.parseInt(iterator2.next().getPhoto());  commented only to overcome error
                    i--;
                }
                CustomListAdapter adapter = new CustomListAdapter(ViewRooms.this, roomNames, roomImages);
                setListAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String room = o.toString();
        Toast.makeText(getApplicationContext(), room, Toast.LENGTH_LONG).show();
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.findRoom(userID+"",room,new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ViewRooms.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("roomID", rooms.get(0).getId());
                editor.commit();
                startActivity(new Intent(ViewRooms.this, viewDevices.class));
            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }

    public void addRoom(View v) {
        startActivity(new Intent(this, addRoomsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_rooms, menu);
        return true;
    }
}



