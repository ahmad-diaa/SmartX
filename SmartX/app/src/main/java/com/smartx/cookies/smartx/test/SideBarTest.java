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
    private String[] titles;
    private int[] icons;

    public SideBarTest(){super(ViewRooms.class);}
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        mRecyclerView=myActivity.getRecyclerView();
        child =myActivity.getChild();
        titles =myActivity.getMAdapter().getTitles();
        icons=myActivity.getMAdapter().getIcons();
    }
    public void testPreconditions() {
        assertNotNull("myActivity is null", myActivity);
    }
    public void testSideBar()throws Exception{

      assertEquals(mRecyclerView.getAdapter().getItemCount(),8);
      assertEquals(mRecyclerView.getAdapter().getItemViewType(0),0);
      assertEquals(mRecyclerView.getAdapter().getItemViewType(5),1);
      assertEquals(mRecyclerView.getChildPosition(child),-1);
      assertEquals(titles[0],"View Favorites");
      assertEquals(titles[1],"View Rooms");
      assertEquals(titles[2],"Edit Information");
      assertEquals(titles[3],"Change Password");
      assertEquals(titles[4],"Contact us");
      assertEquals(titles[5],"About us");
      assertEquals(titles[6],"Logout");
      assertEquals(icons[0],  R.mipmap.star);
      assertEquals(icons[1],R.mipmap.room);
      assertEquals(icons[2],R.mipmap.pencil);
      assertEquals(icons[3],R.mipmap.lock);
      assertEquals(icons[4],R.mipmap.help);
      assertEquals(icons[5],R.mipmap.home);
      assertEquals(icons[6],R.mipmap.bye);

   }
}
