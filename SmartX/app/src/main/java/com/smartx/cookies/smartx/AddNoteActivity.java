package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import models.Note;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddNoteActivity extends Activity {
    String ENDPOINT = "http://192.168.43.249:3000/";
    int userID;
    int roomID;
    String deviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID=(mSharedPreference.getInt("userID",1));
        roomID=(mSharedPreference.getInt("roomID", 1));
//        deviceID = (mSharedPreference.getString("deviceID","0"));
        setContentView(R.layout.activity_add_note);
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
    public void sendNoteToRails(View view) {
        EditText noteBody = (EditText)findViewById(R.id.noteText);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        String body = noteBody.getText().toString();
        myAPI api = adapter.create(myAPI.class);
        Log.i(userID+"", roomID+"ADI EL IDs");
        Log.i(noteBody.getText().toString()  , userID+"ADI EL IDs");

        api.addNote(userID + "", roomID + "", "1", body,new Callback<Note>() {
            @Override
            public void success(Note note, Response response) {
                startActivity(new Intent(getApplicationContext(), ViewNotesActivity.class));

            }

//            @Override
//            public void success(List<Note> notes, Response response) {
//                startActivity(new Intent(getApplicationContext(), ViewNotesActivity.class));
//            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Cannot add note!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
