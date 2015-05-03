package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import models.Session;
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
    String titles[] = {"View Favorites", "View Rooms", "Edit Information", "Change Password", "Contact us", "Report a problem", "About us", "Logout"};
    int icons[] = {R.mipmap.star, R.mipmap.room, R.mipmap.pencil, R.mipmap.lock, R.mipmap.call, R.mipmap.help, R.mipmap.home, R.mipmap.bye};
    String name;
    int profile = R.mipmap.smartorange2;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
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
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        name = (mSharedPreference.getString("Name", ""));
        mAdapter = new SideBarAdapter(titles, icons, name, profile, this);
        mRecyclerView.setAdapter(mAdapter);
        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    switch (recyclerView.getChildPosition(child)) {
                        case 1:
                            startActivity(new Intent(getApplicationContext(), addRoomsActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(getApplicationContext(), ViewRooms.class));
                            break;
                        case 3:
                            startActivity(new Intent(getApplicationContext(), changeInfo.class));
                            break;
                        case 4:
                            startActivity(new Intent(getApplicationContext(), changePassword.class));
                            break;
                        case 5:
                            reportProblemP(child);
                            break;
                        case 6:
                            reportProblemE(child);
                            break;
                        case 7:
                            startActivity(new Intent(getApplicationContext(), About_us.class));
                            break;
                        case 8: logout(child);break;

                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
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


    /**
     * It allows the user to email his problem,
     *
     * @param v the view of the activity
     */

    public void reportProblemE(View v) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ahmaddiaa93@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "My problem is regarding");
        i.putExtra(Intent.EXTRA_TEXT, "Explain Your problem here");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * It allows the user to call the company in order to report his problem,
     *
     * @param v the view of the activity
     */

    public void reportProblemP(View v) {

        String number = "01117976333";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    /**
     * When the user click on logout, this method is called, it sends a request to delete the user's session after it succeed it renders login View after it sends
     *
     * @param v it takes the view
     */
    public void logout(View v) {
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String token = (mSharedPreference.getString("token", "222245asa"));
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = ADAPTER.create(myAPI.class);
        api.logout(userID + "", new Callback<Session>() {
            @Override
            public void success(Session session, Response response) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

    }
}
