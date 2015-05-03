package com.smartx.cookies.smartx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ViewDeviceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_device);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_device, menu);
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
     * This method is called whenever Notes button is clicked
     * to show the notes on that device
     * It renders the ViewNotesActivity
     *
     * @param view It takes the view as a parameter
     */
    public void getNotes(View view) {
        startActivity(new Intent(getApplicationContext(), ViewNotesActivity.class));

    }

    /**
     *
     * @param keyCode The value in event.getKeyCode().
     * @param event Description of the key event.
     * @return If you handled the event, return true. If you want to allow the event
     *          to be handled by the next receiver, return false.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(ViewDeviceActivity.this, viewDevices.class);
            startActivity(i);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
