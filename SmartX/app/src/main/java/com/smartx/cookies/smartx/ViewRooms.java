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

/**
 *SE Sprint1
 *ViewRooms.java
 * Purpose: View rooms and devices in each room.
 *
 * @author Amir
 */

public class ViewRooms extends ListActivity {

    /**
     * Endpoint composed of the ip address of network
    plus port number of server.
     */
    private String endpoint = "http://192.168.1.2:3000/";

    /**
     * Holds user's id saved in shared preferences.
     */
    private int userID;


    /**
     *Called when the activity is starting.
    It shows list of rooms belonging to the user signed in.
     *
     * @param savedInstanceState if the activity is being
    re-initialized after previously being shut down then
    this Bundle  contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);
        final SharedPreferences SHARED_PREFERENCE =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (SHARED_PREFERENCE.getInt("userID", 1));
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(endpoint).build();
        myAPI api = ADAPTER.create(myAPI.class);
        api.viewRooms(userID + "", new Callback<List<Room>>() {

            @Override
            public void success(List<Room> rooms, Response response) {
                String[] roomNames = new String[rooms.size()];
                final Integer[] ROOM_IMAGES = new Integer[rooms.size()];
                Iterator<Room> iterator = rooms.iterator();
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames[i] = iterator.next().getName();
                    i--;
                }
                CustomListAdapter adapter =
                        new CustomListAdapter(ViewRooms.this, roomNames, ROOM_IMAGES);
                setListAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }

    /**
     *This method will be called when an item in the list is selected.
    The name of the room clicked will be used as parameter to
    findRoom method which retrieves from rails the room with given name.
    The devices inside this room will show up.
     *
     * @param l the ListView where the click happened.
     * @param v the view that was clicked within the ListView.
     * @param position the position of the view in the list.
     * @param id the row id of the item that was clicked.
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String room = o.toString();
        Toast.makeText(getApplicationContext(), room, Toast.LENGTH_LONG).show();
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(endpoint).build();
        myAPI api = ADAPTER.create(myAPI.class);
        api.findRoom(userID + "", room, new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {
                SharedPreferences prefs =
                        PreferenceManager.getDefaultSharedPreferences(ViewRooms.this);
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

    /**
     *Initialize the contents of the Activity's options menu
    defined in menu_view_rooms.xml.
     *
     * @return  return true if the menu is to be displayed
    otherwise return false.
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_rooms, menu);
        return true;
    }

    /**
     *get the endpoint composed of the ip address of network
    plus the port number of server.
     *
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     *set the endpoint composed of the ip address of network
    plus the port number of server.
     *
     * @param endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     *get id of the user.
     *
     * @return primary key of the user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     *set id of the user.
     *
     * @param userID the primary key of the user.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
}



