package com.smartx.cookies.smartx;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import models.User;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "DB.db";
    public static final String User_TABLE = "User";
    public static final String User_COLUMN_ID = "id";
    public static final String User_COLUMN_NAME = "name";
    public static final String User_COLUMN_PASSWORD = "password";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table " + User_TABLE +
                        "(" + User_COLUMN_ID + " integer primary key autoincrement, "
                        + User_COLUMN_NAME + " text , " + User_COLUMN_PASSWORD + " text not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + User_TABLE);
        onCreate(db);
    }

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(User_COLUMN_NAME, user.getName());
        contentValues.put(User_COLUMN_PASSWORD, user.getPassword());
        db.insert(User_TABLE, null, contentValues);
        //db.execSQL("INSERT INTO User (name,password) Values (" +user.getName()+", "+user.getPassword()+ ")");
        db.close();
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, User_TABLE);
        return numRows;
    }

    public boolean updateContact(Integer id, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User_COLUMN_NAME, name);
        contentValues.put(User_COLUMN_PASSWORD, password);

        db.update(User_TABLE, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public void deleteUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + User_TABLE + " WHERE " + User_COLUMN_NAME + " =" + name + " ;");
    }


    public Cursor showUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + User_TABLE + " WHERE " + User_COLUMN_NAME + "='" + name + "'", null);
        return c;
    }


    public static class device extends ActionBarActivity {

        Spinner spinner;
        String[] elements = {"TV", "Radio", "Fridge"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_device);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);

            spinner = (Spinner) findViewById(R.id.spinner1);
            spinner.setAdapter(adapter);

        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_device, menu);
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
}