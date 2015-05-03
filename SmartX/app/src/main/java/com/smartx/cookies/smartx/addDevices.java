package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Device;
import models.Session;
import models.Type;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *SE Sprint1
 *addDevices.java
 * Purpose: Add devices to rooms.
 *
 * @author Amir
 */

public class addDevices extends Activity implements AdapterView.OnItemSelectedListener {
    /**
     * Holds user's id saved in shared preferences.
     */
    private int userID;

    /**
     * Holds room's id saved in shared preferences.
     */
    private int roomID;

    /**
     * Shows different types of devices.
     */
    private Spinner deviceSpinner;

    /**
     * Holds controller id entered.
     */
    private EditText deviceID;

    /**
     * Button clicked to add a device in a room.
     */
    private Button addDeviceButton;

    /**
     * Instance of class myApi.
     */
    private myAPI api;
    String titles[] = {"View Favorites","View Rooms","Edit Information","Change Password","Contact us","Report a problem","About us","Logout"};
    int icons[] = {R.mipmap.star,R.mipmap.room,R.mipmap.pencil,R.mipmap.lock,R.mipmap.call,R.mipmap.help,R.mipmap.home,R.mipmap.bye};
    String name ;
    int profile = R.mipmap.smartorange2;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    /**
     *Called when the activity is starting.
     It requests from rails list of available types for devices
     and shows them in the spinner. AddDevice method posts to rails a new device to be created
     for current user in a specific room then Activity ViewDevices
     starts showing all user's devices in this room including the new one.
     *
     * @param savedInstanceState if the activity is being
    re-initialized after previously being shut down then
    this Bundle  contains the data it most recently supplied.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_devices);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final SharedPreferences SHARED_PREFERENCE =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (SHARED_PREFERENCE .getInt("userID", 1));
        roomID = (SHARED_PREFERENCE .getInt("roomID", 1));
        deviceID = (EditText) findViewById(R.id.device_id);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();

        api = adapter.create(myAPI.class);
        deviceSpinner = (Spinner) findViewById(R.id.device_spinner);
        deviceSpinner.setOnItemSelectedListener(this);
        final List<String> devices = new ArrayList<String>();
        api.requestTypes(new Callback<List<Type>>() {

            @Override
            public void success(List<Type> types, Response response) {
                Iterator<Type> iterator = types.iterator();
                while (iterator.hasNext()) {
                    String s = iterator.next().getName();
                    devices.add(s);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });

        devices.add("None");

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, devices);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deviceSpinner.setAdapter(dataAdapter);

        addDeviceButton = (Button) findViewById(R.id.addDeviceButton);
        addDeviceButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((deviceSpinner.getSelectedItem().toString().equals("None")) ||
                        deviceID.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Fill in the Blank spaces", Toast.LENGTH_LONG).show();
                } else {
                    Device device = new Device(deviceSpinner.getSelectedItem().toString(), roomID, userID, deviceID.getText().toString(), "off");
                    api.addDevice(device.getUserID() + " ", device.getRoomID() + "",device.getDeviceID() ,device.getName(), device.getStatus(), new Callback<Device>() {
                        @Override
                        public void success(Device device, Response response) {
                            SharedPreferences prefs =
                                    PreferenceManager.getDefaultSharedPreferences(addDevices.this);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("deviceID", deviceID.getText().toString());
                            editor.commit();
                            api.addClicker(device.getUserID() + " ", device.getRoomID() + "",device.getDeviceID(),device.getDeviceID()+"/false",new Callback<Clicker>() {
                                @Override
                                public void success(Clicker clicker, Response response) {
                                    startActivity(new Intent(getApplicationContext(), viewDevices.class));

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();


                                }
                            });

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getApplicationContext(), "Cannot Add A Device!", Toast.LENGTH_LONG).show();

                        }


                    });


                }
            }

        });

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
                        case 4: Intent rs = new Intent(getApplicationContext(), changePassword.class);
                            rs.putExtra("id", userID);
                            rs.putExtra("flag",0);
                            startActivity(rs);
                            break;
                        case 5: reportProblemP(child);break;
                        case 6: reportProblemE(child);break;
                        case 7: startActivity(new Intent(getApplicationContext(), About_us.class));break;
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

    /**
     *This method will be called when no type for the device is selected.
     *
     * @param arg0 the AdapterView where the click happened.
     */
    public void onNothingSelected(AdapterView arg0) {
        Toast.makeText(arg0.getContext(),"Nothing Selected" , Toast.LENGTH_LONG).show();
    }

    /**
     *This method will be called when a type for the device is selected.
     The name of the type clicked will show up.
     *
     * @param parent the AdapterView where the click happened.
     * @param view the view that was clicked within the ListView.
     * @param position the position of the view in the list.
     * @param id the row id of the item that was clicked.
     */

    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.setSelection(position);
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }





    /**
     *get the button clicked to add a device.
     *
     * @return button
     */
    public Button getButton() {
        return addDeviceButton;
    }

    /**
     *set the button clicked to add a device.
     *
     * @param addDeviceButton
     */
    public void setButton(Button  addDeviceButton) {
        this. addDeviceButton =  addDeviceButton;
    }

    /**
     *get api.
     *
     * @return api
     */
    public myAPI getApi() {
        return api;
    }

    /**
     *set api.
     *
     * @param api
     */
    public void setApi(myAPI api) {
        this.api = api;
    }

    /**
     *get the endpoint composed of the ip address of network
     plus the port number of server.
     *
     * @return the endpoint
     */
    public String getEndpoint() {
        return getResources().getString(R.string.ENDPOINT);
    }


    /**
     *get id of the user.
     *
     * @return primary key of the user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     *set id of the user.
     *
     * @param userID the primary key of the user.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *get id of the room where a device is to be added.
     *
     * @return primary key of the room.
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     *set id of the room where a device is to be added.
     *
     * @param roomID the primary key of the room.
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    /**
     *get the spinner that shows different types of devices.
     *
     * @return spinner
     */
    public Spinner getDeviceSpinner() {
        return deviceSpinner;
    }

    /**
     *set the spinner that shows different types of devices.
     *
     * @param deviceSpinner
     */
    public void setDeviceSpinner(Spinner deviceSpinner) {
        this.deviceSpinner = deviceSpinner;
    }

    /**
     *get the EditText that holds controller id.
     *
     * @return EditText
     */
    public EditText getDeviceID() {
        return deviceID;
    }

    /**
     *set the EditText that holds controller id.
     *
     * @param deviceID
     */
    public void setDeviceID(EditText deviceID) {
        this.deviceID = deviceID;
    }

    /**
     *It allows the user to email his problem,
     * @param v the view of the activity
     */

    public void reportProblemE(View v){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ahmaddiaa93@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "My problem is regarding");
        i.putExtra(Intent.EXTRA_TEXT   , "Explain Your problem here");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *It allows the user to call the company in order to report his problem,
     * @param v the view of the activity
     */

    public void reportProblemP(View v){

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
