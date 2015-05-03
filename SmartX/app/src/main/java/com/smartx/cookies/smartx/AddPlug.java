package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.Session;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *  The purpose of this class is to get the ID and the name of the plug to be added.
 *  @author zamzamy on 1/5/15.
 */
public class AddPlug extends Activity {

    private EditText plugName;
    private EditText plugID;
    private int userID;
    private int roomID;
    private SharedPreferences prefs;
    private Button next;

    /**
     * gets the pug name and the plug ID, checks that they are not null, and passes them to the next page
     *
     * @param savedInstanceState the bundle passed to the Activity from the previous one
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plug);
        plugID = (EditText) findViewById(R.id.plugid);
        plugName = (EditText) findViewById(R.id.plugname);
        next = (Button) findViewById(R.id.nextbutton);
        next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = PreferenceManager.getDefaultSharedPreferences(AddPlug.this);
                SharedPreferences.Editor editor = prefs.edit();
                String plugNameField = plugName.getText().toString();
                String plugIDField = plugID.getText().toString();
                roomID = prefs.getInt("roomID", 1);
                userID = prefs.getInt("userID", 1);


                if (plugNameField.trim().equals("") && plugNameField.trim().equals("")) {
                    Toast.makeText(AddPlug.this, "Plug ID and name required", Toast.LENGTH_SHORT).show();
                } else if (plugNameField.trim().equals("")) {
                    Toast.makeText(AddPlug.this, "Plug name required", Toast.LENGTH_SHORT).show();
                } else if (plugNameField.trim().equals("")) {
                    Toast.makeText(AddPlug.this, "Plug ID required", Toast.LENGTH_SHORT).show();
                }
                editor.putString("plugName", plugName.getText().toString());
                editor.putString("plugID", plugID.getText().toString());
                editor.commit();
                startActivity(new Intent(AddPlug.this, PlugIcon.class));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_plug, menu);
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
}
