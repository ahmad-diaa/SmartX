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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.Session;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * changeInfo.java
 * Purpose: user can edit his contact information (email and phone number)
 *
 * @author Ahmad Abdalraheem
 * @author Amir
 */
public class changeInfo extends Activity {
    EditText emailTxt;
    EditText phoneTxt;
    Button changeInfoB;
    private String email;
    private String phone;
    private String originalPass;
    private int userID;
    String titles[] = {"View Favorites", "View Rooms", "Edit Information", "Change Password", "Contact us", "Report a problem", "About us", "Logout"};
    int icons[] = {R.mipmap.star, R.mipmap.room, R.mipmap.pencil, R.mipmap.lock, R.mipmap.call, R.mipmap.help, R.mipmap.home, R.mipmap.bye};
    String name;
    int profile = R.mipmap.smartorange2;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;

    public String getEmail() {
        return email;
    }
// @param String for setting the email

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
// @param String for setting the phone number

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // @param String for setting the original password
    public String getOriginalPass() {
        return originalPass;
    }

    public void setOriginalPass(String originalPass) {
        this.originalPass = originalPass;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        changeInfoB = (Button) findViewById(R.id.changeInfoB);
        emailTxt = (EditText) findViewById(R.id.email);
        emailTxt.setText(mSharedPreference.getString("email", ""));
        phoneTxt = (EditText) findViewById(R.id.phone);
        phoneTxt.setText(mSharedPreference.getString("phone", ""));
        userID = (mSharedPreference.getInt("userID", 1));
        originalPass = (mSharedPreference.getString("password", "123456"));
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

    /**
     * it takes the input from the user to change his information,it toast whether the information is updated or not,
     *
     * @param v the view of the activity.
     */
    public void changeInfo(View v) {
        email = emailTxt.getText().toString();
        phone = phoneTxt.getText().toString();
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (email.equals(null)) {
            email = (mSharedPreference.getString("email", ""));
        }
        if (phone.equals(null)) {
            phone = (mSharedPreference.getString("phone", ""));
        }
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);

        api.changeInfo(userID + "", email, originalPass, phone, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.putString("email", email);
                editor.putString("phone", phone);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Your information Successfully updated", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ViewRooms.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_info, menu);
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
