package com.smartx.cookies.smartx;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import models.Plug;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Purpose: it vies the plugs of a certain room 
 * @author maggiemoheb
 */
public class ViewPlugs extends  ListActivity {
    int userID;
    int roomID;
    String roomName;
    CustomListDevice adapter2;
    ArrayList<String> plugNames;
    EditText editSearch;

    public void setPlugNames(ArrayList<String> plugNames){
        this.plugNames = plugNames;
    }
    public ArrayList<String> getPlugNames() {
        return this.plugNames;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plugs);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userID = (mSharedPreference.getInt("userID", 1));
        roomID = (mSharedPreference.getInt("roomID", 1));
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);
        Log.d(userID + "", roomID + "");
        api.getRoom(userID + "", roomID + "", new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                roomName = s.replace("%20", " ");
                TextView roomText = (TextView) findViewById(R.id.textView3);
                roomText.setText(roomName);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR ", error.getMessage());
                Toast.makeText(getApplicationContext(), "Something went wrong with room name, please try again", Toast.LENGTH_LONG).show();
            }
        });
        api.viewPlugs(userID + "", roomID + "", new Callback<List<Plug>>() {
            @Override
            public void success(List<Plug> plugs, Response response) {
                plugNames = new ArrayList<String>();
                Iterator<Plug> iterator = plugs.iterator();
                int i = plugs.size() - 1;
                while (i >= 0 & iterator.hasNext()) {
                    plugNames.add(iterator.next().getName());
                    i--;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, plugNames);
                setListAdapter(adapter);
                adapter2 = new CustomListDevice(ViewPlugs.this, plugNames);
                setListAdapter(adapter2);
                editSearch = (EditText) findViewById(R.id.editText);
                // Capture Text in EditText
                editSearch.addTextChangedListener(new TextWatcher() {
                                                              @Override
                                                              public void afterTextChanged(Editable arg0) {
                                                                  // TODO Auto-generated method stub
                                                                  String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                                                                  adapter2.filter2(text);
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
                                                          }
                );
            }

            @Override
            public void failure(RetrofitError error) {
                startActivity(new Intent(getApplicationContext(), ViewPlugs.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_plugs, menu);
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
