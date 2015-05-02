package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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
 * Renders all the plugs found in the room the user selected.
 * It allows control over the plugs such as turning on/off, deletion, renaming, and searching.
 * @author zamzamy 1/5/2015
 */

public class ViewPlugs extends Activity {

    GridView grid;
    RestAdapter adapter;
    myAPI api;

    /**
     * @return the userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @return the roomID.
     */
    public int getRoomID() {
        return roomID;
    }

    SharedPreferences.Editor editor;
    private int userID;
    private int roomID;
    String plugPhoto = "";
    String[] plugNames;
    String[] photos;
    int[] photoID;
    Button addPlug;
    private SharedPreferences prefs;

    /**
     *
     * @param savedInstanceState The previous state of the activity in case the activity crashes and
     *                           is rendered once again.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plugs);
        prefs = PreferenceManager.getDefaultSharedPreferences(ViewPlugs.this);
        editor = prefs.edit();
        grid = (GridView) findViewById(R.id.icongrid);
        adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        api = adapter.create(myAPI.class);
        userID = prefs.getInt("userID", 1);
        roomID = prefs.getInt("roomID", 1);
        api.viewPlugs(userID + "", roomID + "", new Callback<List<Plug>>() {
            @Override
            public void success(List<Plug> plugs, Response response) {
                plugNames = new String[plugs.size()];
                photos = new String[plugs.size()];
                Iterator<Plug> iterator = plugs.iterator();
                int i = 0;
                while (i < plugs.size() & iterator.hasNext()) {
                    Plug cur = iterator.next();
                    plugNames[i] = cur.getName();
                    photos[i] = cur.getPhoto();
                    i++;
                }
                initGrid();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "An error has occurred\n Please try again later", Toast.LENGTH_SHORT).show();
            }
        });

        addPlug = (Button) findViewById(R.id.plugadd);
        addPlug.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddPlug.class));
            }
        });

        Button viewDevices = (Button) findViewById(R.id.viewdevices);
        viewDevices.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), viewDevices.class));
            }
        });
    }

    /**
     * Initializes the grid icons to their corresponding images.
     */
    public void initGrid() {
        photoID = new int[photos.length];

        for (int i = 0; i < photos.length; i++) {
            if (photos[i].equals("plugin")) {
                photoID[i] = R.drawable.plugin;

            } else if (photos[i].equals("switcher")) {
                photoID[i] = R.drawable.switcher;

            } else if (photos[i].equals("fridge")) {
                photoID[i] = R.drawable.fridge;

            } else if (photos[i].equals("lamp")) {
                photoID[i] = R.drawable.lamp;

            } else if (photos[i].equals("fan")) {
                photoID[i] = R.drawable.fan;

            } else if (photos[i].equals("phone")) {
                photoID[i] = R.drawable.phone;

            } else if (photos[i].equals("washmachine")) {
                photoID[i] = R.drawable.washmachine;

            } else if (photos[i].equals("stereo")) {
                photoID[i] = R.drawable.stereo;

            } else if (photos[i].equals("food")) {
                photoID[i] = R.drawable.food;

            } else if (photos[i].equals("computer")) {
                photoID[i] = R.drawable.computer;

            } else if (photos[i].equals("router")) {
                photoID[i] = R.drawable.router;

            } else if (photos[i].equals("microwave")) {
                photoID[i] = R.drawable.microwave;

            } else if (photos[i].equals("playstation")) {
                photoID[i] = R.drawable.playstation;

            }

        }
        CustomGrid adapter = new CustomGrid(ViewPlugs.this, plugNames, photoID);
        grid = (GridView) findViewById(R.id.gridicons);
        grid.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_plugs, menu);
        return true;
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
