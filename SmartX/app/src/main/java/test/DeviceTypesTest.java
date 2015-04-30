package test;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;

import com.smartx.cookies.smartx.CustomListAdapter;
import com.smartx.cookies.smartx.ViewRooms;
import com.smartx.cookies.smartx.deviceList;
import com.smartx.cookies.smartx.myAPI;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;

import models.Device;
import models.Room;
import models.Type;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dalia on 4/8/15.
 */

public class DeviceTypesTest extends ActivityInstrumentationTestCase2<deviceList> {
    private deviceList deviceActivity;

    public DeviceTypesTest() {
        super(deviceList.class);

    }

    private String type;
    private int userID;
    private int roomID;
    private String ENDPOINT;


    protected void setUp() throws Exception {
        super.setUp();
        deviceActivity = getActivity();
        super.setUp();
        type = deviceActivity.getType();
        userID = deviceActivity.getUserID();
        ENDPOINT = deviceActivity.getENDPOINT();

    }

    public void testTypes() {

        ArrayList<Room> rooms = new ArrayList<Room>();
        ArrayList<Device> devices = new ArrayList<Device>();
        ArrayList<String> roomNames = new ArrayList<String>();
        final ArrayList<String> deviceNames = new ArrayList<String>();
        final ArrayList<String> typeRooms = new ArrayList<String>();
        final ArrayList<String> roomIDs = new ArrayList<String>();


        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        myAPI api = adapter.create(myAPI.class);

        deviceActivity.setList(userID);

        type = deviceActivity.getmSharedPreference().getString("deviceType", "TV");
        deviceActivity.getApi().allDevices(userID+"", new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                for(int i=0;i<devices.size();i++)
                if (devices.get(i).getName().equalsIgnoreCase(type)){
                       roomIDs.add(devices.get(i).getRoomID() +"");
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        deviceActivity.getApi().viewRooms(userID + "", new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {
                for (int i = 0; i < rooms.size(); i++) {
                    if (roomIDs.contains(rooms.get(i).getId())){
                        typeRooms.add(rooms.get(i).getName());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        assertEquals(typeRooms, deviceActivity.getRoomNameList());
        assertEquals(type, deviceActivity.getType());
    }

}

