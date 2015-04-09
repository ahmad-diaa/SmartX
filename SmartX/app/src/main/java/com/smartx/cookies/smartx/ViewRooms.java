package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.ListActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import android.widget.Toast;
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * ViewRooms.java
 * Purpose: viewing all the rooms of the user as well as searching for a certain room by name
 *
 * @author Dalia Maarek
 */


public class ViewRooms extends ListActivity {

    private EditText editSearch;
    private int userID;
    Button addRoomB;
    static int count = -1;
    private CustomListAdapter adapter2;
    private int[] photos = new int[]{R.drawable.one,
            R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine};
    private ArrayList<String> roomNames;
    private ArrayList<Integer> iconRooms;

    /**
     *
     * @param adapter2 CustomListAdapter to set
     */
    public void setAdapter2(CustomListAdapter adapter2) {
        this.adapter2 = adapter2;
    }

    /**
     *
     * @param photos Array  of photos to set
     */
    public void setPhotos(int[] photos) {
        this.photos = photos;
    }

    /**
     *
     * @return the customListAdapter
     */
    public CustomListAdapter getAdapter2() {
        return adapter2;
    }

    /**
     *
     * @return ArrayList of all rooms
     */
    public ArrayList<String> getRoomNames() {
        return roomNames;
    }

    /**
     *
     * @return ArrayList of all devices
     */
    public ArrayList<Integer> getIconRooms() {
        return iconRooms;
    }


    /**
     *
     * @param iconRooms Arraylist of Rooms photos ids
     */
    public void setIconRooms(ArrayList<Integer> iconRooms) {
        this.iconRooms = iconRooms;
    }

    /**
     *
     * @param roomNames ArrayList of all rooms
     */
    public void setRoomNames(ArrayList<String> roomNames) {

        this.roomNames = roomNames;
    }

    /**
     * Gets the id of the photo to be assigned to the next room
     *
     * @return An integer number between 0 and 8
     */
    public int randomIcon() {

        count = (count + 1) % 9;
        return count;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);
        count = -1;
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewRooms(userID + "", new Callback<List<Room>>() {

            @Override
            public void success(List<Room> rooms, Response response) {
                roomNames = new ArrayList<String>();
                Iterator<Room> iterator = rooms.iterator();
                iconRooms = new ArrayList<Integer>();
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames.add(iterator.next().get_roomName());
                    iconRooms.add(photos[randomIcon()]);
                    i--;
                }
                adapter2 = new CustomListAdapter(ViewRooms.this, roomNames, iconRooms);
                setListAdapter(adapter2);
                editSearch = (EditText) findViewById(R.id.search);

                // Capture Text in EditText

                editSearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                        adapter2.filter(text);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
                registerForContextMenu(getListView());


            }


            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }

    /**
     * opens the activity (ViewDevices) of the clicked room
     *
     * @param list     List of all rooms
     * @param view     The clicked listview
     * @param position The position of the view in the list
     * @param id       The row id of the item that was clicked
     */
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ViewRooms.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("userID", userID);
        editor.putInt("roomID", position);
        editor.commit();
        startActivity(new Intent(this, viewDevices.class));
        this.setTitle("View Rooms");
    }

    /**
     * Starts the activity (addRoomsActivity) to create a new room
     *
     * @param view add room button
     */
    public void addRoom(View view) {
        startActivity(new Intent(this, addRoomsActivity.class));
    }

    /**
     * Creates the initial menu state
     *
     * @param menu Menu to be populated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_rooms, menu);
        return true;
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
        menu.setHeaderTitle("Context menu");
        menu.add(0, v.getId(), 0, "Rename Room");
        menu.add(0, v.getId(), 0, "Delete Room");
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
        if (item.getTitle() == "Rename") {
            Toast.makeText(this, "Rename Action Should be invoked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "Delete") {
            Toast.makeText(this, "Delete Action should be invoked", Toast.LENGTH_SHORT).show();
        } else {
            return false;
        }
        return true;
    }
}
