package com.smartx.cookies.smartx.test;



import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.smartx.cookies.smartx.R;
import com.smartx.cookies.smartx.ViewRooms;


/**
 * Created by Amir on 5/1/2015.
 */
public class SideBarTest extends ActivityInstrumentationTestCase2<ViewRooms> {
    private ViewRooms myActivity;
    private RecyclerView mRecyclerView;
    private View child;

    public SideBarTest(){super(ViewRooms.class);}
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        mRecyclerView=myActivity.getRecyclerView();
        child =myActivity.getChild();
    }
    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }
    public void testSideBar()throws Exception{
      assertEquals(mRecyclerView.getAdapter().getItemCount(),8);
      assertEquals(mRecyclerView.getAdapter().getItemViewType(0),0);
      assertEquals(mRecyclerView.getAdapter().getItemViewType(5),1);
      assertEquals(mRecyclerView.getChildPosition(child),-1);
   }
}
