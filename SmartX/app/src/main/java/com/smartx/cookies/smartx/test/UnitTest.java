package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;
import com.smartx.cookies.smartx.CustomListAdapter;
import com.smartx.cookies.smartx.ViewRooms;
import java.util.ArrayList;


/**
 * Created by ahmaddiaa on 4/6/15.
 */
public class UnitTest extends ActivityInstrumentationTestCase2<ViewRooms> {

    private ViewRooms myRooms;

    public UnitTest() {
        super(ViewRooms.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        myRooms = getActivity();
    }

    public void testFilter() throws Exception {
        ArrayList<String> rooms = new ArrayList<String>();
        ArrayList<Integer> photos = new ArrayList<Integer>();
        rooms.add("Kitchen");
        rooms.add("Kitchen2");
        rooms.add("Bathroom");
        rooms.add("Bedroom");
        rooms.add("ahmad's kitchen");
        for (int i = -1; i < rooms.size() - 1; i++)
            photos.add((i + 1) % 9);

        myRooms.setRoomNames(rooms);
        myRooms.setIconRooms(photos);

        assertEquals(rooms, myRooms.getRoomNames());
        assertEquals(photos, myRooms.getIconRooms());

        CustomListAdapter adapter2 = new CustomListAdapter(myRooms, rooms, photos);
        adapter2.filter("Kit");
        ArrayList<String> searchedRooms = new ArrayList<String>();
        searchedRooms.add("Kitchen");
        searchedRooms.add("Kitchen2");
        searchedRooms.add("ahmad's kitchen");
        ArrayList<Integer> searchedRoomsPhotos = new ArrayList<Integer>();
        searchedRoomsPhotos.add(0);
        searchedRoomsPhotos.add(1);
        searchedRoomsPhotos.add(4);

        assertEquals(searchedRooms, adapter2.getItemName());
        assertEquals(searchedRoomsPhotos, adapter2.getImgId());

        adapter2.filter("");

        assertEquals(myRooms.getRoomNames(), adapter2.getItemName());
        assertEquals(myRooms.getIconRooms(), adapter2.getImgId());

        adapter2.filter("ahmed");

        assertEquals(adapter2.getItemName(), new ArrayList<String>());
        assertEquals(adapter2.getImgId(), new ArrayList<Integer>());

    }
}
