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
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewRooms extends ListActivity {

    String ENDPOINT = "http://192.168.1.106:3000/";
    int userID;
    Button addRoomB;
    int count = -1;
    int[] photos = new int[]{ R.drawable.one ,
            R.drawable.two ,R.drawable.three ,R.drawable.four ,R.drawable.five ,
            R.drawable.six ,R.drawable.seven ,R.drawable.eight ,R.drawable.nine};

    public int randomIcon(){
        count++;
        return (count + 1)%9 ;
    }
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
                //Iterator<Room> iterator2 = rooms.iterator();
                //ArrayList<Integer> photoss = new ArrayList<Integer>();
                Integer [] iconRooms = new Integer[rooms.size()];
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames[i] = iterator.next().get_roomName();
                    // roomImages[i] = Integer.parseInt(iterator2.next().getPhoto());
                    iconRooms[i] = photos[randomIcon()];
                    i--;
                }
                CustomListAdapter adapter = new CustomListAdapter(ViewRooms.this, roomNames, iconRooms);
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

    public void viewByType(View v){
        showPopUp();
    }
    private void showPopUp() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                this);

        builderSingle.setTitle("Select a device type");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("TVs");
        arrayAdapter.add("Air Conditioners");
        arrayAdapter.add("Curtains");
        arrayAdapter.add("Plugs");
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
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                ViewRooms.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
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
