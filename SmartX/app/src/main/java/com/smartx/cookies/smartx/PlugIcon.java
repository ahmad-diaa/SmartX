package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import models.Plug;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PlugIcon extends Activity {

    GridView grid;
    RestAdapter adapter;
    myAPI api;
    SharedPreferences.Editor editor;
    private int userID;
    private int roomID;
    Button done;
    String plugName;
    String plugID;
    String plugPhoto = "";
    private SharedPreferences prefs;

    public SharedPreferences getMyPrefs() {
        return prefs;
    }

    /**
     * Called when the activity starts. it gets the plug name and id from the previous activity
     * through the shared preferences and waits for the user to choose an icon for the plug and
     * then initiates the new plug and returns back to viewDevices.class
     * @param savedInstanceState if the activity is being
    re-initialized after previously being shut down then
    this Bundle  contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plug_icon);
        grid = (GridView) findViewById(R.id.icongrid);
        adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        api = adapter.create(myAPI.class);
        initGrid();
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (prefs.getInt("userID", 1));
        roomID = (prefs.getInt("roomID", 1));
        editor = prefs.edit();
        plugName = prefs.getString("plugName", "null");
        plugID = prefs.getString("plugID", "null");
        done = (Button) findViewById(R.id.donebutton);
        done.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plugPhoto.equals("")) {
                    Toast.makeText(PlugIcon.this, "Please choose an icon first", Toast.LENGTH_SHORT).show();
                } else {
                    if (plugName.equals("null") || plugID.equals("null"))
                        Toast.makeText(PlugIcon.this, "An Error Occurred :(\n Please Try again later", Toast.LENGTH_SHORT);
                    else {
                        Plug plug = new Plug(plugName, roomID, userID, plugID + "", "off", plugPhoto + "");
                        api.addPlug(plug.getUserID() + "", plug.getRoomID() + "", plug.getPlugID() + "", plug.getName(), plug.getStatus(), plug.getPhoto() + "", new Callback<Plug>() {

                            @Override
                            public void success(Plug plug, Response response) {
                                //Toast.makeText(PlugIcon.this, "Yeah babyyyy", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PlugIcon.this, viewDevices.class));
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                throw error;
                            }
                        });

                    }
                }
            }
        });
    }

    /**
     * Initializes the grid icons to their corresbonding images.
     */
    public void initGrid() {
        final ImageAdapter imageAdapter = new ImageAdapter(this);
        grid.setAdapter(imageAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        plugPhoto = "plugin";
                        break;

                    case 1:
                        plugPhoto = "switcher";
                        break;

                    case 2:
                        plugPhoto = "fridge";
                        break;

                    case 3:
                        plugPhoto = "lamp";
                        break;

                    case 4:
                        plugPhoto = "fan";
                        break;

                    case 5:
                        plugPhoto = "phone";
                        break;

                    case 6:
                        plugPhoto = "washmachine";
                        break;

                    case 7:
                        plugPhoto = "stereo";
                        break;

                    case 8:
                        plugPhoto = "food";
                        break;

                    case 9:
                        plugPhoto = "computer";
                        break;

                    case 10:
                        plugPhoto = "router";
                        break;

                    case 11:
                        plugPhoto = "microwave";
                        break;

                    case 12:
                        plugPhoto = "playstation";
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plug_icon, menu);
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
