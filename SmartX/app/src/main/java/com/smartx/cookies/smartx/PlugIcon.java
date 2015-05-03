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
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import models.Plug;
import models.Session;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author  zamzamy on 1/5/15.
 * The purpose of this activity is to allow the user to choose the icon of the plug to be added.
 */

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
                                startActivity(new Intent(PlugIcon.this, ViewPlugs.class));
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
                        plugPhoto = "home";
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
