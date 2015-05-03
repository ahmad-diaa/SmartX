package com.smartx.cookies.smartx.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import com.smartx.cookies.smartx.AddPlug;
import com.smartx.cookies.smartx.PlugIcon;
import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.myAPI;
import com.smartx.cookies.smartx.viewDevices;
import models.Plug;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zamzamy on 5/1/15.
 */
public class AddPlugTest extends InstrumentationTestCase {
    private AddPlug myActivity; // An instance of the AddPlugActivity
    private PlugIcon myActivity2; // An instance of the PlugIconActivity
    private EditText plugName;
    private EditText plugID;
    int userID;//the user ID of the AddPlugActivity
    int roomID;//the room ID of the AddPlugActivity

    /**
     * Tests AddPlug.class as well as PlugIcon.class and tests whether the new plug is found or not
     */
    public void testAddPlugActivity(){
        Instrumentation instrumentation = getInstrumentation();
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(AddPlug.class.getName(), null, false);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setClassName(instrumentation.getTargetContext(), AddPlug.class.getName());
        instrumentation.startActivitySync(intent);
        Activity currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
        assertNotNull("AddPlug is null", currentActivity);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(String.valueOf(R.string.ENDPOINT)).build();
        myAPI api = adapter.create(myAPI.class);

        userID = 1;
        roomID = 1;

        plugName = (EditText) currentActivity.findViewById(R.id.plugname);
        plugID = (EditText) currentActivity.findViewById(R.id.plugid);
        assertNotNull("plug name textfield is null", plugName);
        assertNotNull("plug id textfield is null", plugID);
        TouchUtils.clickView(this, plugName);
        instrumentation.sendStringSync("Zamzamy's phone");

        TouchUtils.clickView(this, plugID);
        instrumentation.sendStringSync("123456");

        Button nextButton = (Button) currentActivity.findViewById(R.id.nextbutton);
        assertNotNull(nextButton);
        TouchUtils.clickView(this, nextButton);

        monitor = instrumentation.addMonitor(PlugIcon.class.getName(), null, false);
        intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setClassName(instrumentation.getTargetContext(), PlugIcon.class.getName());
        instrumentation.startActivitySync(intent);

        Activity NextActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
        assertNotNull("PlugIcon Activity is null", NextActivity);

        GridView grid = (GridView) NextActivity.findViewById(R.id.icongrid);
        assertNotNull("Icon Grid is null", grid);
        TouchUtils.clickView(this, grid.getChildAt(4));

        Button DoneButton = (Button) NextActivity.findViewById(R.id.donebutton);
        assertNotNull("Done Button is null", DoneButton);
        TouchUtils.clickView(this, DoneButton);

        monitor = instrumentation.addMonitor(viewDevices.class.getName(), null, false);
        intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setClassName(instrumentation.getTargetContext(), viewDevices.class.getName());
        instrumentation.startActivitySync(intent);

        Activity ThirdActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
        assertNotNull("ViewDevices Activity is null", ThirdActivity);

        api.getPlug(userID+"", roomID+"", "123456", new Callback<Plug>() {
            @Override
            public void success(Plug plug, Response response) {
                assertEquals(plug.getName(), "Zamzamy's phone");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }}
