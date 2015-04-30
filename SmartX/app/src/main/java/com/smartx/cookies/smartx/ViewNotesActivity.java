package com.smartx.cookies.smartx;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Note;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Purpose: View notes left by users on a certain device
 *
 * @author maggiemoheb
 * @author ahmaddiaa
 */
public class ViewNotesActivity extends ListActivity {

    private myAPI api;
    private int roomID;
    private int userID;
    private String deviceID;

    /**
     * gets the user's id , room's id and device's id to be used in intializing the list of devices.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        final SharedPreferences sharedPreference =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        roomID = (sharedPreference.getInt("roomID", 1));
        userID = (sharedPreference.getInt("userID", 1));
        deviceID = (sharedPreference.getString("deviceID", "1"));
        setList();
    }

    /**
     *  Gets notes from rails server and adds it to the list to be viewed.
     */
    public void setList() {
        final RestAdapter adapter =
                new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        api.getNotes(userID + "", roomID + "", deviceID, new Callback<List<Note>>() {

            /**
             * Shows a list of the notes left on this device.
             */
            @Override
            public void success(List<Note> notes, Response response) {
                ArrayList<String> deviceNotes = new ArrayList<String>();
                Iterator<Note> iterator = notes.iterator();

                while (iterator.hasNext()) {
                    deviceNotes.add(iterator.next().getBody().replace("%20", " "));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNotes) {

                    /**
                     * Changes the listView's font colour to light blue.
                     */
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                        text.setTextColor(Color.parseColor("#ADD8E6"));
                        return view;
                    }
                };
                setListAdapter(adapter);
            }

            /**
             * Shows an error message in case of failing to get the notes from rails server.
             */
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_notes, menu);
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
     * This method is called whenever the Add Note button is clicked in the ViewNotesActivity
     *
     * @param view it takes the view
     */
    public void addNote(View view) {
        startActivity(new Intent(getApplicationContext(), AddNoteActivity.class));
    }
}
