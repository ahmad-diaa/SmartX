package com.smartx.cookies.smartx;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * changePasssword.java
 * Purpose: user can change his password
 *
 * @author Ahmad Abdalraheem
 */
public class changePassword extends Activity {
    Button changePasswordB;
    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;
    private String oldPass;
    private String originalPass;
    private String newPass;
    private String confPass;
    private int userID;
    String titles[] = {"View Favorites","View Rooms","Edit Information","Change Password","Contact us","About us","Logout"};
    int icons[] = {R.mipmap.star,R.mipmap.room,R.mipmap.pencil,R.mipmap.lock,R.mipmap.help,R.mipmap.home,R.mipmap.bye};
    String name ;
    int profile = R.mipmap.smartorange2;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;

    public String getOldPass() {
        return oldPass;
    }

    // @param String for setting the old password
    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getOriginalPass() {
        return originalPass;
    }

    // @param String for setting the original password
    public void setOriginalPass(String originalPass) {
        this.originalPass = originalPass;
    }

    public String getNewPass() {
        return newPass;
    }

    // @param String for setting the new password
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfPass() {
        return confPass;
    }

    // @param String for setting the confirmation password
    public void setConfPass(String confPass) {
        this.confPass = confPass;
    }

    public int getUserID() {
        return userID;
    }

    // @param String for setting the user id
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getENDPOINT() {
        return getResources().getString(R.string.ENDPOINT);
    }
    // @param String for setting the endpoint


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        changePasswordB = (Button) findViewById(R.id.changePasswordButton);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        originalPass = (mSharedPreference.getString("password", "123456"));
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
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

    /**
     * it takes the input from the user to change his password,in case the process of changing passsword succeed it renders a login view, otherwise it toasts an error message,
     *
     * @param v the view of the activity which consists of 3 textfields and a button
     */
    public void changePassword(View v) {
        oldPass = oldPassword.getText().toString();
        newPass = newPassword.getText().toString();
        confPass = confirmPassword.getText().toString();
        if (!newPass.equals(confPass)) {
            Toast.makeText(getApplicationContext(), "Password and confirm password are not the same", Toast.LENGTH_LONG).show();
        } else if (newPass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Please make sure your password at least 6 characters", Toast.LENGTH_LONG).show();
        } else if (!originalPass.equals(oldPass)) {
            Toast.makeText(getApplicationContext(), "Please make sure you entered the correct password", Toast.LENGTH_LONG).show();
        }
        if (newPass.equals(confPass) && originalPass.equals(oldPass)) {
            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
            myAPI api = adapter.create(myAPI.class);
            api.changePassword(userID + "", newPass, new Callback<models.User>() {

                @Override
                public void success(models.User user, Response response) {
                    oldPass = newPass;
                    Toast.makeText(getApplicationContext(), "Your password is successfully changed",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "Make sure you are online", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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
