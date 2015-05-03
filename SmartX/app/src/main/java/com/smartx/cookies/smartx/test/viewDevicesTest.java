package com.smartx.cookies.smartx.test;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.viewDevices;

/**
 * @author maggiemoheb
 */
public class viewDevicesTest extends ActivityInstrumentationTestCase2<viewDevices> {
    private viewDevices myActivity;//An instance of the viewDevices
    private EditText roomName;//An instance of EditText field in the RenameRoomActivity
    private Button renameButton; //An instance of EditText field in the RenameRoomActivity
    private int userID;//The user ID of the RenameRoomActivity
    private int roomID;//The room ID of the RenameRoomActivity
    private String message;//The message of the viewDevice Activity

    /**
     * A constructor that matches the super constructor
     */
    public viewDevicesTest() {
        super(viewDevices.class);
    }

    /**
     * setUp() sets the instance variables to match those of the RenameRoomActivity it is testing
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        roomName = (EditText) myActivity.findViewById(R.id.rename_room);
        renameButton = (Button) myActivity.findViewById(R.id.rename_button);
        userID = myActivity.getUserID();
        roomID = myActivity.getRoomID();
        message = myActivity.getErrorMessage();
    }

    public void testViewNotesSuccess() throws Exception {
        myActivity.renderViewNotes(0, userID, roomID);
        String activityMessage = myActivity.getErrorMessage();
        assertEquals("Selected Successfully", activityMessage);
    }


}