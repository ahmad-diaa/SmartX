package com.smartx.cookies.smartx;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import models.Room;
import models.Type;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * ViewRooms.java
 * Purpose: viewing all the rooms of the user as well as searching for a certain room by name
 *
 * @author Dalia Maarek
 * @author Ahmad Abdalraheem
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
    private myAPI api;
    private int itemPosition;
    /**
     * @param adapter2 CustomListAdapter to set
     */

    public void setAdapter2(CustomListAdapter adapter2) {
        this.adapter2 = adapter2;
    }
    /**
     * @param photos Array  of photos to set
     */

    public void setPhotos(int[] photos) {
        this.photos = photos;
    }

    /**
     * @return the customListAdapter
     */

    public CustomListAdapter getAdapter2() {
        return adapter2;
    }

    /**
     * @return ArrayList of all rooms
     */

    public ArrayList<String> getRoomNames() {
        return roomNames;
    }

    /**
     * @return ArrayList of all devices
     */

    public ArrayList<Integer> getIconRooms() {
        return iconRooms;
    }


    /**
     * @param iconRooms Arraylist of Rooms photos ids
     */
    
    public void setIconRooms(ArrayList<Integer> iconRooms) {
        this.iconRooms = iconRooms;
    }

    /**
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
        public int randomIcon () {

            count = (count + 1) % 9;
            return count;
        }

    public void viewByType(View v) {
        showPopUp();
    }

    private void showPopUp() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                this);

        builderSingle.setTitle("Select a device type");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.select_dialog_singlechoice);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);


        api.requestTypes(new Callback<List<Type>>() {
            @Override
            public void success(List<Type> types, Response response) {

                Iterator<Type> iterator = types.iterator();
                while (iterator.hasNext()) {
                    String s = iterator.next().getName();
                    arrayAdapter.add(s);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String strName = arrayAdapter.getItem(which);
                        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = mSharedPreference.edit();
                        editor.putString("deviceType", strName);
                        editor.commit();
                        startActivity(new Intent(getBaseContext(), deviceList.class));

                    }

                });
        builderSingle.show();
    }

    /**
     * Called when the activity is starting.
     * It shows list of rooms belonging to the user signed in.
     *
     * @param savedInstanceState if the activity is being
     *                           re-initialized after previously being shut down then
     *                           this Bundle  contains the data it most recently supplied.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);
        final SharedPreferences SHARED_PREFERENCE =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (SHARED_PREFERENCE.getInt("userID", 1));
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        api = ADAPTER.create(myAPI.class);
        api.viewRooms(userID + "", new Callback<List<Room>>() {

            @Override
            public void success(List<Room> rooms, Response response) {

                roomNames = new ArrayList<String>();
                Iterator<Room> iterator = rooms.iterator();

                iconRooms = new ArrayList<Integer>();
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames.add(iterator.next().getName().replace("%20", " "));
                    iconRooms.add(photos[randomIcon()]);
                    i--;
                }
                adapter2 = new CustomListAdapter(ViewRooms.this, roomNames, iconRooms);
                setListAdapter(adapter2);
                editSearch = (EditText) findViewById(R.id.search);
                            editSearch.addTextChangedListener(new
                    TextWatcher() {

                        @Override
                        public void afterTextChanged (Editable arg0){
                            // TODO Auto-generated method stub
                            String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                            adapter2.filter(text);
                        }

                        @Override
                        public void beforeTextChanged (CharSequence arg0,int arg1,
                        int arg2, int arg3){
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onTextChanged (CharSequence arg0,int arg1, int arg2,
                        int arg3){
                            // TODO Auto-generated method stub
                        }
                    }
                    );
                    registerForContextMenu(getListView()
                    );
                }

                @Override
                public void failure (RetrofitError error){
                    Log.d("", error.getMessage());
                    throw error;
                }
            }
            );
        }

                /**
                 * This method will be called when an item in the list is selected.
                 * The name of the room clicked will be used as parameter to
                 * findRoom method which retrieves from rails the room with given name.
                 * The devices inside this room will show up.
                 *
                 * @param l        the ListView where the click happened.
                 * @param v        the view that was clicked within the ListView.
                 * @param position the position of the view in the list.
                 * @param id       the row id of the item that was clicked.
                 */

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Object o = this.getListAdapter().getItem(position);
        String room = o.toString();
        room = room.replace(" ", "%20");
        final RestAdapter ADAPTER =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = ADAPTER.create(myAPI.class);
        api.findRoom(userID + "", room, new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {
                SharedPreferences prefs =
                        PreferenceManager.getDefaultSharedPreferences(ViewRooms.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("roomID", rooms.get(0).getId());
                editor.commit();
                startActivity(new Intent(ViewRooms.this, viewDevices.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR505", error.getMessage());
                throw error;
            }
        });
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
     * Starts the Activity (changePassword) to change user's  password,
     * @param v the view of the activity
     */

    public void changePassword(View v) {
        startActivity(new Intent(this, changePassword.class));
    }
    /**
     * Starts the Activity (changeInfo) to change user's information,
     * @param v the view of the activity
     */

    public void changeInfo(View v) {
        startActivity(new Intent(this, changeInfo.class));
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
     * get id of the user.
     *
     * @return primary key of the user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * set id of the user.
     *
     * @param userID the primary key of the user.
     */
    public void setUserID(int userID) {
        this.userID = userID;
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
        if (item.getTitle() == "Rename Room") {


            String roomSelected = getListView().getItemAtPosition(itemPosition).toString();


            api.findRoom(userID + "", roomSelected.replace(" ","%20"), new Callback<List<Room>>() {
                @Override
                public void success(List<Room> rooms, Response response) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ViewRooms.this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("roomID", rooms.get(0).getId());
                    editor.commit();
                    startActivity(new Intent(ViewRooms.this, renameRoomActivity.class));
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });

        } else if (item.getTitle() == "Delete Room") {
            Toast.makeText(this, "Delete Action should be invoked", Toast.LENGTH_SHORT).show();
        } else {
            return false;
        }
        return true;
    }
}



