package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import models.Session;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * purpose:This class creates an instance of a clicker that will allow the user to control a device.
 *
 * @author youmna
 */
public class LampClickerActivity extends ActionBarActivity {
    int userID; //store the current userID
    int roomID;//store the current roomID
    String deviceID;//store the current deviceID
    static int clickerID;//store the current clickerID
    String command;//store the current command
    boolean on;//initial current state of device
    int brightness;//controls the brightness level
    SharedPreferences mSharedPreference;//Used to get data from previous sessions
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
     * userId getter
     *
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * roomId getter
     *
     * @return RoomID
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * deviceId getter
     *
     * @return deviceID
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * current status getter
     *
     * @return status
     */
    public boolean getOn() {
        return on;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_clicker);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        deviceID = (mSharedPreference.getString("deviceID", "1"));
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.getClicker(userID + "", roomID + "", deviceID + "", new Callback<Clicker>() {

            @Override
            public void success(Clicker clicker, Response response) {
                clickerID = clicker.getClickerId();

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
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
                            Intent rs = new Intent(getApplicationContext(), changePassword.class);
                            rs.putExtra("id", userID);
                            rs.putExtra("flag", 0);
                            startActivity(rs);
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
                        case 8:
                            logout(child);
                            break;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lamp_clicker, menu);
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
     * called  if the Switch "on/off" in lamp_clicker layout is clicked.
     * will send the current command to the Clicker
     *
     * @param v
     */
    public void LampOnOff(View v) {
        on = !on;
        if (on) {
            command = new String(deviceID + "/lamp/0");
        } else {
            command = new String(deviceID + "/lamp/1");
        }
        sendCommand();
    }

    /**
     * called  if the button '+' in lamp_clicker layout is clicked.
     * will send the current command to the Clicker
     *
     * @param v
     */
    public void dim(View v) {
        if (brightness == 0) {
            Toast.makeText(getApplicationContext(), "Lamp is off", Toast.LENGTH_SHORT).show();

        } else {
            brightness--;
            Toast.makeText(getApplicationContext(), "decrease brightness" + deviceID, Toast.LENGTH_SHORT).show();
        }
        command = new String(deviceID + "/dim/1");
        sendCommand();


    }

    /**
     * called  if the button '-' in lamp_clicker layout is clicked.
     * will send the current command to the Clicker
     *
     * @param v
     */
    public void bright(View v) {
        if (brightness == 10) {
            Toast.makeText(getApplicationContext(), " max brightness", Toast.LENGTH_SHORT).show();

        } else {
            brightness++;
            Toast.makeText(getApplicationContext(), "increase brightness" + deviceID, Toast.LENGTH_SHORT).show();
        }
        command = new String(deviceID + "/bright/1");
        sendCommand();


    }

    /**
     * called if the device was switched on ,it updates the current clicker command to the recently entered one
     */
    public void sendCommand() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.sendClickerCommand(userID + "", roomID + "", deviceID, clickerID + "", command, new Callback<Clicker>() {
            @Override
            public void success(Clicker clicker, Response response) {
                Toast.makeText(getApplicationContext(), "Command sent", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong with the clicker!", Toast.LENGTH_SHORT).show();
            }
        });
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
