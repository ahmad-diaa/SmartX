package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import models.Room;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;



public class ViewRooms extends ListActivity{

    String ENDPOINT = "http://192.168.25.27:3000/";
    int userID;
    Button addRoomB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_view_rooms);
       // addRoomB = (Button) findViewById(R.id.addRoomB);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID=(mSharedPreference.getInt("userID", 1));
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
       // final ListView ListView = (ListView) findViewById(R.id.listview);
        myAPI api = adapter.create(myAPI.class);
        api.viewRooms(userID +"", new Callback<List<Room>>() {

            @Override
            public void success(List<Room> rooms, Response response) {

                String[] roomNames = new String[rooms.size()];
                final Integer[] roomImages = new Integer[rooms.size()];
  //              List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

                Iterator<Room> iterator = rooms.iterator();
                Iterator<Room> iterator2 = rooms.iterator();

                int i = rooms.size() - 1;
                while(i>= 0 & iterator.hasNext()){
                roomNames[i] = iterator.next().get_roomName();
                roomImages[i] = Integer.parseInt(iterator2.next().getPhoto());
                i--;
                }
    
                CustomListAdapter adapter=new CustomListAdapter(ViewRooms.this, roomNames, roomImages);
                setListAdapter(adapter);


            }

            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(getApplicationContext(), About_us.class));

            }
        });



    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(this, addRoomsActivity.class));
        this.setTitle("View Rooms");

    }

    public void addRoom (View v) {
        startActivity(new Intent(this, addRoomsActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_rooms, menu);
        return true;
    }
//    public void onListItemClick(ListView lv ,View view,int position,int imgid) {
//
//        String Slecteditem= (String)getListAdapter().getItem(position);
//        Toast.makeText(this, Slecteditem, Toast.LENGTH_SHORT).show();
//    }


}
