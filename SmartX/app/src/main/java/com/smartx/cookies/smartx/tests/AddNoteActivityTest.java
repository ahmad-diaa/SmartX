package com.smartx.cookies.smartx.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.smartx.cookies.smartx.AddNoteActivity;
import com.smartx.cookies.smartx.R;

/**
 * Created by maggiemoheb on 4/8/15.
 */
public class AddNoteActivityTest extends ActivityInstrumentationTestCase2<AddNoteActivity> {
    private AddNoteActivity myActivity;
    private EditText noteBody;
    private Button addNote;
    String ENDPOINT;
    int userID;
    int roomID;
    String deviceID;
    String Body;

    public AddNoteActivityTest() {
        super(AddNoteActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        noteBody = (EditText) myActivity.findViewById(R.id.noteText);
        addNote = (Button) myActivity.findViewById(R.id.button3);
        this.ENDPOINT = myActivity.getENDPOINT();
        this.userID = myActivity.getUserID();
        this.roomID = myActivity.getRoomID();
        this.deviceID = myActivity.getDeviceID();
        this.Body = myActivity.getBody();
    }


    public void testNotebody() {
        assertNotNull("Text Field is null", noteBody);
    }

    public void testAddNote() {
        assertNotNull("Button is null", addNote);
    }


    public void testENDPOINT() {
        assertNotNull("ENDPOINT is null", ENDPOINT);

    }

    public void testDeviceID() {
        assertNotNull("deviceID is null", deviceID);

    }

    public void testBody() {
        assertNotNull("body is null", Body);
    }

    public void testAddNoteSuccess() {
        myActivity.setBody("tester");
        myActivity.sendNoteToRails();
        assertEquals("", myActivity.getErrorMessage());
    }

    public void testAddNoteFailure() {
        myActivity.setBody("");
        myActivity.sendNoteToRails();
        assertEquals("Cannot add note!", myActivity.getErrorMessage());
    }


}
