package com.example.protocols_androidclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;


public class CheckerBoard extends Activity implements OnClickListener{
    protected GridLayout gameGridLayout;
    protected static ImageView[][] gameViewGrid;
    protected static TextView player1;
    protected static TextView player2;
    protected static ImageView redCol1;
    protected static ImageView blackCol1;

    protected static ImageView redCol2;
    protected static ImageView blackCol2;

    protected static ImageView redCol3;
    protected static ImageView blackCol3;

    protected static ImageView redCol4;
    protected static ImageView blackCol4;

    protected static ImageView redCol5;
    protected static ImageView blackCol5;

    protected static ImageView redCol6;
    protected static ImageView blackCol6;

    protected static ImageView redCol7;
    protected static ImageView blackCol7;
    protected static ImageView redCol8;
    protected static ImageView blackCol8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker_board);


        //gameGridLayout = (GridLayout) findViewById(R.id.GridLayout1);
        gameGridLayout.setOnClickListener(this);
        gameViewGrid = new ImageView[8][8];

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checker_board, menu);
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

    @Override
    public void onClick(View view) {

    }
}
