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
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import models.Note;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Purpose: This class is used to add a note to a specific device
 *
 * @author maggiemoheb
 * @author Amir
 */

public class AddNoteActivity extends Activity {
    int userID; // ID of the user of this session
    int roomID; //ID of the room of the device
    String deviceID; //ID of the device to which the note is added
    String Body = "";//The body of the note
    String errorMessage = "initial"; //error message which indicates if the adding ended up in success or failure
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
     * Getter for the user ID
     *
     * @return userID
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Getter for the room ID
     *
     * @return roomID
     */
    public int getRoomID() {
        return this.roomID;
    }

    /**
     * Getter for the device ID
     *
     * @return deviceID
     */
    public String getDeviceID() {
        return this.deviceID;
    }

    /**
     * Getter for the error message
     *
     * @return errorMessage
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Getter for the body
     *
     * @return Body
     */
    public String getBody() {
        return this.Body;
    }

    /**
     * Setter for the body of the note
     *
     * @param s: The body is changed to the value of this String
     */
    public void setBody(String s) {
        this.Body = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        deviceID = (mSharedPreference.getString("deviceID", "1"));
        Log.d("ID1", userID + "");
        Log.d("ID2", roomID + "");
        Log.d("ID3", deviceID);
        setContentView(R.layout.activity_add_note);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                        case 5: reportProblemP(child);break;
                        case 6: reportProblemE(child);break;
                        case 7: startActivity(new Intent(getApplicationContext(), About_us.class));break;
                        case 8: startActivity(new Intent(getApplicationContext(), addRoomsActivity.class));break;
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
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
     * This method us called whenever Add Note button is clicked.
     * It puts what is written in EditText in the Body instance variable.
     *
     * @param view: It takes the view as a parameter.
     */
    public void AddNote(View view) {
        EditText noteBody = (EditText) findViewById(R.id.noteText);
        Body = noteBody.getText().toString();
        this.sendNoteToRails();

    }

    /**
     * This method is called whenever adding a note to the database is needed (in the test and AddNote() method).
     * It calls API.AddNote() method passing Body instance method in the method to be sent to rails to create a note.
     */
    public void sendNoteToRails() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        if (Body.equals("")) {
            errorMessage = "Cannot add note!";
        } else {
            errorMessage = "";
        }
        api.addNote(userID + "", roomID + "", deviceID, Body, new Callback<Note>() {
            @Override
            public void success(Note note, Response response) {
                startActivity(new Intent(getApplicationContext(), ViewNotesActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Cannot add note!", Toast.LENGTH_LONG).show();
            }
        });


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
}
