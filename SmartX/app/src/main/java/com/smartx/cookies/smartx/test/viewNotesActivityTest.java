package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.ViewNotesActivity;
import com.smartx.cookies.smartx.myAPI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import models.Note;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author ahmaddiaa.
 */
public class viewNotesActivityTest extends ActivityInstrumentationTestCase2<ViewNotesActivity> {

    private ViewNotesActivity myActivity;
    private int userID;
    private int roomID;
    private String deviceID = "123456";
    private String ENDPOINT;
    private ArrayList<String> Notes = new ArrayList<String>();
    private Button addNote;
    public viewNotesActivityTest() {
        super(ViewNotesActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        addNote = (Button)
                myActivity
                        .findViewById(R.id.button2);
        userID = 1;
        roomID = 1;
        ENDPOINT = "http://172.20.10.3:3000/";
    }

    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }

    /**
     * Verifies that the button is displayed correctly and it has the correct layout.
     */
    public void testAddNoteButton ()throws Exception
    {

        final View decorView = myActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, addNote);
        final ViewGroup.LayoutParams layoutParams =
                addNote.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * Compares the expected notes to the notes returned from rails server.
     */
    public void testViewNotes() throws Exception {
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                Notes.add("Please don't close it");
                Notes.add("Keep the volume down");
            }
        });


        final RestAdapter adapter =
                new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);
        api.getNotes(userID + "", roomID + "", deviceID, new Callback<List<Note>>() {

            @Override
            public void success(List<Note> notes, Response response) {

                ArrayList<String> deviceNotes = new ArrayList<String>();
                Iterator<Note> iterator = notes.iterator();

                while (iterator.hasNext()) {
                    deviceNotes.add(iterator.next().getBody().replace("%20", " "));
                }
                assertEquals(Notes, deviceNotes);
            };

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

}
