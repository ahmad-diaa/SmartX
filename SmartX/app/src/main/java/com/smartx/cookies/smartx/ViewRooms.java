package com.smartx.cookies.smartx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.app.ListActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import models.Room;
import models.Type;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewRooms extends ListActivity {

    private String ENDPOINT = "http://192.168.43.100:3000/";
    private int userID;
    private int count = -1;
    private ArrayList<String> roomNames;
    CustomListAdapter cAdapter;
    private ArrayList<Integer> iconRooms;
    private int[] photos = new int[]{R.drawable.one,
            R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine};

    public int randomIcon() {
        count++;
        return (count + 1) % 9;
    }

    public CustomListAdapter getcAdapter() {
        return cAdapter;
    }

    public void setcAdapter(CustomListAdapter cAdapter) {
        this.cAdapter = cAdapter;
    }

    public ArrayList<String> getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(ArrayList<String> roomNames) {
        this.roomNames = roomNames;
    }

    public ArrayList<Integer> getIconRooms() {
        return iconRooms;
    }

    public void setIconRooms(ArrayList<Integer> iconRooms) {
        this.iconRooms = iconRooms;
    }

    public int[] getPhotos() {
        return photos;
    }

    public void setPhotos(int[] photos) {
        this.photos = photos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);

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
                cAdapter = new CustomListAdapter(ViewRooms.this, roomNames, iconRooms);
                setListAdapter(cAdapter);
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

    public void viewByType(View v) {
        showPopUp();
    }

    private void showPopUp() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                this);

        builderSingle.setTitle("Select a device type");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_singlechoice);
//        arrayAdapter.add("TV");
//        arrayAdapter.add("Air Conditioner");
//        arrayAdapter.add("Curtain");
//        arrayAdapter.add("Plug");

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);


        api.requestTypes(new Callback<List<Type>>() {
            @Override
            public void success(List<Type> types, Response response) {

                Iterator<Type> iterator = types.iterator();
                while (iterator.hasNext()) {
                    String s = iterator.next().getName();
                    arrayAdapter.add(s);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String strName = arrayAdapter.getItem(which);
                        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = mSharedPreference.edit();
                        editor.putString("deviceType", strName);
                        editor.commit();
                        startActivity(new Intent(getBaseContext(), deviceList.class));

                    }

                });
        builderSingle.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_rooms, menu);
        return true;
    }
}
