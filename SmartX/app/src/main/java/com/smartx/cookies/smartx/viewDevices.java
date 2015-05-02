package com.smartx.cookies.smartx;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import models.Type;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * SE Sprint2
 * viewDevices.java
 * Purpose: Display list of devices & view devices in a certain room
 *
 * @author Amir
 * @author maggiemoheb
 */
public class viewDevices extends ListActivity {
    int userID;
    int roomID;
    String roomName;
    Button addDevice;
    int itemPosition;
    ArrayList<String> deviceNames;
    String message = "";

    /**
     * A getter to the user ID
     *
     * @return the user ID of the session
     */
    public int getUserID() {
        return this.userID;
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
                throw error;
            }
        });
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
            Toast.makeText(this, "Delete Action should be invoked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "View Notes") {
            renderViewNotes(itemPosition, userID, roomID);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Renders view notes view for the device of the context menu
     *
     * @param itemPosition
     * @param user
     * @param room
     */
    public void renderViewNotes(int itemPosition, int user, int room) {
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
                startActivity(new Intent(viewDevices.this, ViewNotesActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        message = "Selected Successfully";
    }
}
