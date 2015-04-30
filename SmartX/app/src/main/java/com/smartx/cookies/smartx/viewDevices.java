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
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class viewDevices extends ListActivity {

    int userID;
    int roomID;
    String roomName;
    Button addDevice;
    ArrayList<String> deviceNames;
    String titles[] = {"View Favorites","View Rooms","Edit Information","Change Password","Contact us","About us","Logout"};
    int icons[] = {R.mipmap.star,R.mipmap.room,R.mipmap.pencil,R.mipmap.lock,R.mipmap.help,R.mipmap.home,R.mipmap.bye};
    String name ;
    int profile = R.mipmap.smartorange2;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_devices);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        addDevice = (Button) findViewById(R.id.addDevice);
        addDevice.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewDevices.this, addDevices.class));
            }
        });

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        Log.d(userID + "", roomID + "");
        api.getRoom(userID + "", roomID + "", new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                roomName = s.replace("%20", " ");
                TextView roomText = (TextView) findViewById(R.id.textView6);
                roomText.setText(roomName);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR ", error.getMessage());
                Toast.makeText(getApplicationContext(), "Something went wrong with room name, please try again", Toast.LENGTH_LONG).show();
            }
        });
        api.viewDevices(userID + "", roomID + "", new Callback<List<Device>>() {

            @Override
            public void success(List<Device> devices, Response response) {
                deviceNames = new ArrayList<String>();
                Iterator<Device> iterator = devices.iterator();
                int i = devices.size() - 1;
                while (i >= 0 & iterator.hasNext()) {

                    deviceNames.add(iterator.next().getName());
                    i--;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNames);
                setListAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(getApplicationContext(), viewDevices.class));
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_devices, menu);
        return true;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String device = o.toString();
        Toast.makeText(getApplicationContext(), device, Toast.LENGTH_LONG).show();
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = ADAPTER.create(myAPI.class);
        api.findDevice(userID + "", roomID + "", device, new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                SharedPreferences prefs =
                        PreferenceManager.getDefaultSharedPreferences(viewDevices.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("deviceID", devices.get(0).getDeviceID() + "");
                editor.commit();
                startActivity(new Intent(getApplicationContext(), TvClickerActivity.class));

            }


            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }
}