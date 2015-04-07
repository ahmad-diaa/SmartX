package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.ListActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewRooms extends ListActivity {

    String ENDPOINT = "http://172.20.10.3:3000/";
    EditText editSearch;
    int userID;
    Button addRoomB;
    static int count = -1;
    public CustomListAdapter adapter2;
    int[] photos = new int[]{R.drawable.one,
            R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine};
    public ArrayList<String> roomNames;
    public ArrayList<Integer> iconRooms;

    public void setIconRooms(ArrayList<Integer> iconRooms) {
        this.iconRooms = iconRooms;
    }

    public void setRoomNames(ArrayList<String> roomNames) {

        this.roomNames = roomNames;
    }

    public int randomIcon() {
        count++;
        count = (count + 1) % 9;
        return count;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);
        count = -1;
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewRooms(userID + "", new Callback<List<Room>>() {

            @Override
            public void success(List<Room> rooms, Response response) {
                roomNames = new ArrayList<String>();
                Iterator<Room> iterator = rooms.iterator();
                iconRooms = new ArrayList<Integer>();
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames.add(iterator.next().get_roomName());
                    iconRooms.add(photos[randomIcon()]);
                    i--;
                }
                adapter2 = new CustomListAdapter(ViewRooms.this, roomNames, iconRooms);
                setListAdapter(adapter2);
                editSearch = (EditText) findViewById(R.id.search);

                // Capture Text in EditText

                editSearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                        adapter2.filter(text);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
            }


            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ViewRooms.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("userID", userID);
        editor.putInt("roomID", position);
        editor.commit();
        startActivity(new Intent(this, viewDevices.class));
        this.setTitle("View Rooms");
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
