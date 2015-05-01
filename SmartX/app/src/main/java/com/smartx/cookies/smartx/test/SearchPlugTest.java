package com.smartx.cookies.smartx.test;

import android.test.ActivityInstrumentationTestCase2;

import com.smartx.cookies.smartx.CustomListDevice;
import com.smartx.cookies.smartx.ViewPlugs;

import java.util.ArrayList;


/**
 * Purpose: to test the search of the plugs by name
 * @author maggiemoheb
 */
public class SearchPlugTest extends ActivityInstrumentationTestCase2<ViewPlugs> {
    private ViewPlugs myPlugs; // instance of the activity

    /**
     * constructor of test
     */
    public SearchPlugTest() {
        super(ViewPlugs.class);
    }

    /**
     * sets up the instance variable
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        myPlugs = getActivity();
    }

    /**
     * It tests the method filter
     * @throws Exception
     */
    public void testFilter() throws Exception {
        ArrayList<String> plugs = new ArrayList<String>();
        plugs.add("phone");
        plugs.add("phone2");
        plugs.add("iron");
        plugs.add("microwave");
        myPlugs.setPlugNames(plugs);
        assertEquals(plugs, myPlugs.getPlugNames());
        CustomListDevice adapter2 = new CustomListDevice(myPlugs, plugs);
        adapter2.filter2("phon");
        ArrayList<String> searchedRooms = new ArrayList<String>();
        searchedRooms.add("phone");
        searchedRooms.add("phone2");
        assertEquals(searchedRooms, adapter2.getItemName());
        adapter2.filter2("");
        assertEquals(myPlugs.getPlugNames(), adapter2.getItemName());
        adapter2.filter2("fridge");
        assertEquals(adapter2.getItemName(), new ArrayList<String>());
    }
}
