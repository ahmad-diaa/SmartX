package com.smartx.cookies.smartx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.app.ListActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewRooms extends ListActivity {

    String ENDPOINT = "http://172.20.10.4:3000/";
    int userID;
    Button addRoomB;
    String [] roomNames;
    int count = -1;
    String [] menuItems = {"Delete", "Rename"};
    int[] photos = new int[]{ R.drawable.one ,
            R.drawable.two ,R.drawable.three ,R.drawable.four ,R.drawable.five ,
            R.drawable.six ,R.drawable.seven ,R.drawable.eight ,R.drawable.nine};

    public int randomIcon(){
        count++;
        return (count + 1)%9 ;
    }
    public CustomListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomListAdapter adapter) {
        this.adapter = adapter;
    }

    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.viewRooms(userID + "", new Callback<List<Room>>() {

            @Override
            public void success(List<Room> rooms, Response response) {
                String[] roomNames = new String[rooms.size()];
                final Integer[] roomImages = new Integer[rooms.size()];
                Iterator<Room> iterator = rooms.iterator();
                //Iterator<Room> iterator2 = rooms.iterator();
                //ArrayList<Integer> photoss = new ArrayList<Integer>();
                Integer [] iconRooms = new Integer[rooms.size()];
                int i = rooms.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    roomNames[i] = iterator.next().get_roomName();
                    // roomImages[i] = Integer.parseInt(iterator2.next().getPhoto());
                    iconRooms[i] = photos[randomIcon()];
                    i--;
                }
                CustomListAdapter adapter = new CustomListAdapter(ViewRooms.this, roomNames, iconRooms);
                setListAdapter(adapter);
                registerForContextMenu(getListView());
            }

            @Override
            public void failure(RetrofitError error) {
                throw error;
            }
        });
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ViewRooms.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("userID", userID);
        editor.putInt("roomID", position);
        editor.commit();
        startActivity(new Intent(this, viewDevices.class));
        this.setTitle("View Rooms");
    }

    public void addRoom(View v) {
        startActivity(new Intent(this, addRoomsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_rooms, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Rename");
        menu.add(0, v.getId(), 0, "Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Rename") {
            Toast.makeText(this, "Action 1 invoked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "Delete") {
            Toast.makeText(this, "Action 2 invoked", Toast.LENGTH_SHORT).show();
        } else {
            return false;
        }
        return true;
    }


//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        if (v.equals(this)) {
//            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//            menu.setHeaderTitle(roomNames[info.position]);
//            for (int i = 0; i<menuItems.length; i++) {
//                menu.add(Menu.NONE, i, i, menuItems[i]);
//            }
//        }
//    }
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
//        int menuItemIndex = item.getItemId();
//        String menuItemName = menuItems[menuItemIndex];
//        String listItemName = roomNames[info.position];
//
//        TextView text = (TextView)findViewById(R.id.footer);
//        text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
//        return true;
//    }

}
