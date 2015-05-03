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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.Room;
import models.Session;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * SE Sprint1
 * addRoomsActivity.java
 * Purpose: Add rooms.
 *
 * @author Amir
 */

public class addRoomsActivity extends Activity {

    /**
     * Button clicked to add a room.
     */
    private Button addRoomButton;

    /**
     * Used in randomizing photos for rooms.
     */
    private static int count = -1;

    /**
     * Holds user's id saved in shared preferences.
     */
    private int userID;


    /**
     * Holds 9 different photos for rooms.
     */
    private int[] photos = new int[]{R.drawable.one,
            R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine};
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
     * Called when the activity is starting.
     * Get userID from shared preferences and Get button from View.
     *
     * @param savedInstanceState if the activity is being
     *                           re-initialized after previously being shut down then
     *                           this Bundle  contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrooms);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final SharedPreferences SHARED_PREFERENCE =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (SHARED_PREFERENCE.getInt("userID", 1));

    addRoomButton=(Button)findViewById(R.id.addRoomButton);
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
                        case 4:  Intent rs = new Intent(getApplicationContext(), changePassword.class);
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
 * This method is used to randomize photos for rooms.
 *
 * @return integer value from zero to eight to select a photo for the room.
 */
public int randomIcon(){
        count=(count+1)%9;
        return count;
        }

/**
 * This method is called when the button is clicked.
 addRoom method posts to rails a new room to be created for current user then
 Activity ViewRooms starts showing all user's rooms including the new one.
 *
 * @param v the view that was clicked.
 */
public void addRoomButton(View v){
        EditText roomName=(EditText)findViewById(R.id.roomName);
        Room room=new Room(roomName.getText().toString().replace(" ","%20"));
        RestAdapter adapter=new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api=adapter.create(myAPI.class);
        room.setPhoto(photos[randomIcon()]+"");

        api.addRoom((userID+""),room.getName(),new Callback<Room>(){
@Override
public void success(Room room,Response response){
        SharedPreferences prefs=
        PreferenceManager.getDefaultSharedPreferences(addRoomsActivity.this);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("roomID",room.getId());
        editor.commit();
        startActivity(new Intent(getApplicationContext(),ViewRooms.class));
        }

@Override
public void failure(RetrofitError error){
        Toast.makeText(getApplicationContext(), "Cannot add room!", Toast.LENGTH_LONG).show();

        }
        });

        }


/**
 *get the button clicked to add a room.
 *
 * @return button
 */
public Button getButton(){
        return addRoomButton;
        }

/**
 *set the button clicked to add a room.
 *
 * @param addRoomButton
 */
public void setButton(Button addRoomButton){
        this.addRoomButton=addRoomButton;
        }

/**
 *get photos for rooms.
 *
 * @return photos
 */
public int[]getPhotos(){
        return photos;
        }

/**
 *set photos for rooms.
 *
 * @param photos
 */
public void setPhotos(int[]photos){
        this.photos=photos;
        }

/**
 *get the endpoint composed of the ip address of network
 plus the port number of server.
 *
 * @return the endpoint
 */
public String getEndpoint(){
        return getResources().getString(R.string.ENDPOINT);
        }


/**
 *get id of the user.
 *
 * @return primary key of the user.
 */
public int getUserID(){
        return userID;
        }

/**
 *set id of the user.
 *
 * @param userID the primary key of the user.
 */
public void setUserID(int userID){
        this.userID=userID;
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
