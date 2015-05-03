package com.smartx.cookies.smartx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author zamzamy 1/5/2015
 */
public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] plugNames;
    private final int[] plugPhotos;

    public CustomGrid(Context c, String[] names, int[] photos) {
        mContext = c;
        this.plugPhotos = photos;
        this.plugNames = names;
    }

    /**
     * This methid returns the number of plugs found in the array of plugs.
     *
     * @return the number of plugs in the grid.
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return plugNames.length;
    }

    /**
     * @param position the position of a certain element in the grid.
     * @return the object found at the given position.
     * This method is useless to us so it returns a null by default.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * @param position the position of a certain element in the grid.
     * @return the id of the element found at the given position.
     * This method is useless so it returns a 0 by default.
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * create a new ImageView for each item initialized by the Adapter which is
     * used to render photos of the grid in ViewPlugs.class
     *
     * @param position    position of the element to be initialized
     * @param convertView the view of the element to be initialized, which is bull for the first time.
     * @param parent      parent of the element to be initialized
     * @return the view of the initialized elements
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            //grid.setLayoutParams(new GridView.LayoutParams(150, 150));
            //grid.setPadding(50, 50, 50, 50);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            textView.setText(plugNames[position]);
            imageView.setImageResource(plugPhotos[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}