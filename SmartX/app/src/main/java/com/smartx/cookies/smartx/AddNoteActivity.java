package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    String deviceID = "1";
    String Body = "";
    //boolean flag = true;
    String errorMessage = "initial";
    public int getUserID() {
        return this.userID;
    }
    public int getRoomID() {
        return  this.roomID;
    }
    public String getDeviceID() {
        return this.deviceID;
    }
    public String getENDPOINT() {
        return this.ENDPOINT;
    }
    public String getErrorMessage() {
        return this.errorMessage;
    }
    public String getBody() {
        return this.Body;
    }
    public void setBody(String s) {
        this.Body = s;
    }
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
    public void AddNote(View view) {
        EditText noteBody = (EditText)findViewById(R.id.noteText);
        Body = noteBody.getText().toString();
        this.sendNoteToRails();

    }
    public void sendNoteToRails() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        if(Body.equals("")) {
            errorMessage = "Cannot add note!";
        }
        else {
            errorMessage = "";
        }
        api.addNote(userID + "", roomID + "", deviceID, Body,new Callback<Note>() {
            @Override
            public void success(Note note, Response response) {
               // errorMessage = "";
               // Log.i("success message", errorMessage);
              //  flag = true;
                startActivity(new Intent(getApplicationContext(), ViewNotesActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
              //  flag = false;
                //errorMessage = "Cannot add note!";
                //Log.i("error message", errorMessage);
                Toast.makeText(getApplicationContext(), "Cannot add note!", Toast.LENGTH_LONG).show();
            }
        });


    }
}
