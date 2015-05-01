package com.smartx.cookies.smartx;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/*
    Purpose: Displays a list of rooms that have a selected type of device
     @author Dalia Maarek
 */
public class deviceList extends ListActivity {
    private static int userID;
    private String type;
    private ArrayList<String> roomNameList;
    private ArrayAdapter<String> adapter2;
    private myAPI api;
    private SharedPreferences mSharedPreference;
    String titles[] = {"View Favorites","View Rooms","Edit Information","Change Password","Contact us","About us","Logout"};
    int icons[] = {R.mipmap.star,R.mipmap.room,R.mipmap.pencil,R.mipmap.lock,R.mipmap.help,R.mipmap.home,R.mipmap.bye};
    String name ;
    int profile = R.mipmap.smartorange2;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;

    /*
    @return mSharedPreferences Shared Preference used in app
     */
    public SharedPreferences getmSharedPreference() {
        return mSharedPreference;
    }

    /*
        @return User ID
     */
    public static int getUserID() {
        return userID;
    }

    /*
        @params User ID
     */
    public static void setUserID(int userID) {
        deviceList.userID = userID;
    }
    /*
    @return type of device selected
     */

    public String getType() {
        return type;
    }

    /*
    @returns api API class used to connect to rails
     */
    public myAPI getApi() {
        return api;
    }

    /*
    @params api API class used to connect to rails
     */
    public void setApi(myAPI api) {
        this.api = api;
    }

    /*
        @params type Type of device
     */
    public void setType(String type) {
        this.type = type;
    }

    /*
    @returns roomNameList containing all the names of rooms with a selected type of device
     */
    public ArrayList<String> getRoomNameList() {
        return roomNameList;
    }

    /*
    @return ENDPOINT IP address to connect to
     */
    public String getENDPOINT() {
        return getResources().getString(R.string.ENDPOINT);
    }


    /*
    @params roomNameList sets array of room names with a selected type of device
     */
    public void setRoomNameList(ArrayList<String> roomNameList) {
        this.roomNameList = roomNameList;
    }

    /*
    @return adapter2 ArrayAdapter used in view
     */
    public ArrayAdapter<String> getAdapter2() {
        return adapter2;
    }

    /*
    @params adapter2 ArrayAdapter used in view
     */
    public void setAdapter2(ArrayAdapter<String> adapter2) {
        this.adapter2 = adapter2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        type = mSharedPreference.getString("deviceType", "TV");
        userID = (mSharedPreference.getInt("userID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        api = adapter.create(myAPI.class);
        roomNameList = new ArrayList<String>();
        adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, roomNameList);
        setList(userID);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        name = (mSharedPreference.getString("Name", ""));
        mAdapter = new SideBarAdapter(titles,icons,name,profile,this);
        mRecyclerView.setAdapter(mAdapter);
        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    Drawer.closeDrawers();
                    switch (recyclerView.getChildPosition(child)){
                        case 1: startActivity(new Intent(getApplicationContext(), addRoomsActivity.class));break;
                        case 2: startActivity(new Intent(getApplicationContext(), ViewRooms.class));break;
                        case 3: startActivity(new Intent(getApplicationContext(), changeInfo.class));break;
                        case 4: startActivity(new Intent(getApplicationContext(), changePassword.class));break;
                        case 5: startActivity(new Intent(getApplicationContext(), addRoomsActivity.class));break;
                        case 6: startActivity(new Intent(getApplicationContext(), About_us.class));break;
                        case 7: startActivity(new Intent(getApplicationContext(), addRoomsActivity.class));break;
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
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,R.string.openDrawer,R.string.closeDrawer){

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

    /*

    This method gets all the rooms that have an ID number matching that of a device's room ID with a selected
    type

    @params UserID user Id of current user
     */
    public void setList(int UserID) {
        api.allDevices(userID + "", new Callback<List<Device>>() {

                    @Override
                    public void success(List<Device> devices, Response response) {
                        Iterator<Device> iterator = devices.iterator();
                        Iterator<Device> iterator2 = devices.iterator();
                        while (iterator.hasNext()) {
                            if (type.equalsIgnoreCase(iterator2.next().getName())) {
                                int roomid = iterator.next().getRoomID();
                                api.getRoom(userID + "", roomid + "", new Callback<String>() {
                                    @Override
                                    public void success(String room, Response response) {
                                        room = room.replace("%20", " ");
                                        roomNameList.add(room);
                                        setListAdapter(adapter2);

                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        //add toast
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });
                            } else {
                                iterator.next();
                            }
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, roomNameList);
                            setListAdapter(adapter2);

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}