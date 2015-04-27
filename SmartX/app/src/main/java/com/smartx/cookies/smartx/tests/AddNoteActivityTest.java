package com.smartx.cookies.smartx.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.smartx.cookies.smartx.AddNoteActivity;
import com.smartx.cookies.smartx.R;

/**
 * Purpose: This class is a test for AddNoteActivity.
 *
 * @author maggiemoheb.
 */

public class AddNoteActivityTest extends ActivityInstrumentationTestCase2<AddNoteActivity> {
    private AddNoteActivity myActivity; // An instance of the AddNoteActivity
    private EditText noteBody;//An instance of the EditText field in AddNoteActivity
    private Button addNote;//An instance of the Add Note Button in AddNoteActivity
    String ENDPOINT;//the ENDPOINT of the AddNoteActivity
    int userID;//the user ID of the AddNoteActivity
    int roomID;//the room ID of the AddNoteActivity
    String deviceID;//the device ID of the AddNoteActivity
    String Body;//the Body text of the note of the AddNoteActivity

    /**
     * A constructor matching the super class
     */
    public AddNoteActivityTest() {
        super(AddNoteActivity.class);
    }

    /**
     * This method is called at the beginning of each test
     * It sets up the values of the instance variables to be equal to the ones
     * of the activity it is testing
     */
    public void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        noteBody = (EditText) myActivity.findViewById(R.id.noteText);
        addNote = (Button) myActivity.findViewById(R.id.button3);
        this.userID = myActivity.getUserID();
        this.roomID = myActivity.getRoomID();
        this.deviceID = myActivity.getDeviceID();
        this.Body = myActivity.getBody();
    }

    /**
     * Testing that the text field is set correctly
     */
    public void testNotebody() {

        assertNotNull("Text Field is null", noteBody);
    }

    /**
     * Testing that the button is set correctly
     */
    public void testAddNote() {

        assertNotNull("Button is null", addNote);
    }

    /**
     * Testing that the ENDPOINT is set correctly
     */
    public void testENDPOINT() {
        assertNotNull("ENDPOINT is null", ENDPOINT);

    }

    /**
     * Testing that the device ID is set correctly
     */
    public void testDeviceID() {
        assertNotNull("deviceID is null", deviceID);

    }

    /**
     * Testing that the body is set correctly
     */
    public void testBody() {
        assertNotNull("body is null", Body);
    }

    /**
     * Testing that the note added successfully if the note body is not empty
     */
    public void testAddNoteSuccess() {
        myActivity.setBody("tester");
        myActivity.sendNoteToRails();
        assertEquals("", myActivity.getErrorMessage());
    }

    /**
     * Testing that the note failed to be added if the note body is empty
     */
    public void testAddNoteFailure() {
        myActivity.setBody("");
        myActivity.sendNoteToRails();
        assertEquals("Cannot add note!", myActivity.getErrorMessage());
    }


}
