package com.smartx.cookies.smartx;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
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
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
 *SE Sprint2
 *viewDevices.java
 *Purpose: Display list of device in a certain room
 *
 * @author Amir
 * @author maggiemoheb
 * @author Dalia Maarek
 */

public class viewDevices extends ListActivity {
    int userID;
    int roomID;
    String roomName;
    Button addDevice;
    Button addPlug;
    int itemPosition;
    ArrayList<String> deviceNames;
    String titles[] = {"View Favorites", "View Rooms", "Edit Information", "Change Password", "Contact us", "Report a problem", "About us", "Logout"};
    int icons[] = {R.mipmap.star, R.mipmap.room, R.mipmap.pencil, R.mipmap.lock, R.mipmap.call, R.mipmap.help, R.mipmap.home, R.mipmap.bye};
    String name;
    int profile = R.mipmap.smartorange2;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    String message = "";
    String deviceIDTest =""; //for test

    public String getDeviceIDTest() {
        return deviceIDTest;
    }

    /**
     * A getter to the room ID
     *
     * @return the room ID of the activity
     */
    public int getRoomID() {
        return this.roomID;
    }
    /**
     * A getter to the errorMessage
     *
     * @return the errorMessage of the activity
     */
    public String getErrorMessage() {
        return this.message;
    }

    /**
     * A getter to the user ID
     *
     * @return the user ID of the session
     */
    public int getUserID() {
        return this.userID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_devices);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        addPlug = (Button) findViewById(R.id.addplug);
        addPlug.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewDevices.this, AddPlug.class));
            }
        });

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
                Toast.makeText(getApplicationContext(), "Something went wrong with room name, please try again", Toast.LENGTH_SHORT).show();
            }
        });
        api.viewDevices(userID + "", roomID + "", new Callback<List<Device>>() {

            @Override
            public void success(List<Device> devices, Response response) {
                deviceNames = new ArrayList<String>();
                Iterator<Device> iterator = devices.iterator();
                int i = devices.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    registerForContextMenu(getListView());
                    deviceNames.add(iterator.next().getName());
                    i--;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNames);
                setListAdapter(adapter);
                registerForContextMenu(getListView());
            }
            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(getApplicationContext(), viewDevices.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_devices, menu);
        return true;
    }
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        final String device = o.toString();
        Toast.makeText(getApplicationContext(), device, Toast.LENGTH_SHORT).show();
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        final myAPI api = ADAPTER.create(myAPI.class);
        api.findDevice(userID + "", roomID + "", device, new Callback<List<Device>>() {
                    @Override
                    public void success(final List<Device> devices, Response response) {
                        SharedPreferences prefs =
                                PreferenceManager.getDefaultSharedPreferences(viewDevices.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("deviceID", devices.get(0).getDeviceID() + "");
                        editor.commit();
                        Toast.makeText(getApplicationContext(), devices.get(0).getName(), Toast.LENGTH_SHORT).show();
                        api.findClickerType(devices.get(0).getName(), new Callback<List<Type>>() {
                            @Override
                            public void success(List<Type> types, Response response) {
                                int type = types.get(0).getId();
                                if (type == 1 || type == 4 || type == 5) {
                                    startActivity(new Intent(getApplicationContext(), TvClickerActivity.class));
                                } else {
                                    if (type == 2) {
                                        startActivity(new Intent(getApplicationContext(), LampClickerActivity.class));
                                    } else {
                                        if (type == 3) {
                                            startActivity(new Intent(getApplicationContext(), CurtainClickerActivity.class));
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), defaultClickerActivity.class));
                                        }
                                    }
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                }

        );
    }
    /**
     * Called when the context menu for this view is being built.
     *
     * @param menu     The context menu that is being built.
     * @param v        The view for which the context menu is being built.
     * @param menuInfo Extra information about the item for which the context menu should be shown. This
     *                 information will vary depending on the class of v.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        itemPosition = info.position;
        menu.setHeaderTitle("Context menu");
        menu.add(0, v.getId(), 0, "Add To Favorites");
        menu.add(0, v.getId(), 0, "Delete Device");
        menu.add(0, v.getId(), 0, "View Notes");
    }
    /**
     * Executes commands found in the context menu
     *
     * @param item The item clicked in the context menu
     * @return boolean true in case item clicked corresponds to an action and executed
     * else returns false in case
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Add To Favorites") {
            String deviceSelected = getListView().getItemAtPosition(itemPosition).toString();
            final RestAdapter ADAPTER =
                    new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
            myAPI api = ADAPTER.create(myAPI.class);
            api.findDevice(userID + "", roomID + "", deviceSelected.replace(" ", "%20"), new Callback<List<Device>>() {
                @Override
                public void success(List<Device> devices, Response response) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(viewDevices.this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("deviceID", devices.get(0).getDeviceID());
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), AddToFavorites.class));
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        } else if (item.getTitle() == "Delete Device") {
            AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(
                    this);
            confirmationDialog.setMessage("Are you sure you want to delete this device?");
            confirmationDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            confirmationDialog.setPositiveButton("Confirm",   new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteDevice(userID, roomID, itemPosition);
                }
            });
            confirmationDialog.show();
        } else if (item.getTitle() == "View Notes") {
            renderViewNotes(itemPosition, userID, roomID);
        } else {
            return false;
        }
        return true;
    }
    /*
 @param userID user's ID
 @param roomID room's ID
 @param itemPosition position of item in list

 This method deletes the device that is selected, after the user has confirmed deletion
  */
    public void deleteDevice(final int userID, final int roomID, int itemPosition ){
        final String deviceSelected = getListView().getItemAtPosition(itemPosition).toString();
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        final myAPI api = ADAPTER.create(myAPI.class);
        api.findDevice(userID + "", roomID + "", deviceSelected.replace(" ", "%20"), new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                String id = devices.get(0).getId();
                deviceIDTest = id;
                api.deleteDevice(userID + "", roomID + "", id, new Callback<Device>() {
                    @Override
                    public void success(Device device, Response response) {
                        Toast.makeText(getApplicationContext(), deviceSelected + " has been successfully deleted!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "Could not delete.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * Renders view notes view for the device of the context menu
     *
     * @param itemPosition
     * @param user
     * @param room
     */

    public void renderViewNotes (int itemPosition, int user, int room) {
        String deviceSelected = getListView().getItemAtPosition(itemPosition).toString();
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = ADAPTER.create(myAPI.class);
        api.findDevice(user + "", room + "", deviceSelected.replace(" ", "%20"), new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(viewDevices.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("deviceID", devices.get(0).getId());
                editor.commit();
                startActivity(new Intent (viewDevices.this, ViewNotesActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        message = "Selected Successfully";
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


