package com.smartx.cookies.smartx;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * Created by zamzamy on 4/30/15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    /**
     * Constructor thhat initializes the Context of the adapter the given context
     * @param c
     */
    public ImageAdapter(Context c) {
        mContext = c;
    }

    /**
     *
     * @returns the number of photos found in the grid
     */
    public int getCount() {
        return mThumbIds.length;
    }

    /**
     *
     * @param position the position in which the item found there should be returned.
     * @returns the the item found at the given position.
     */
    public Object getItem(int position) {
        return null;
    }

    /**
     *
     * @param position the position in which the item found there should have its ID returned.
     * @returns the id of the item found at the given position.
     */
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes

                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(23, 23, 23, 23);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    /**
     *
     * @returns the photo array
     */
    public Integer[] getPhotos() {
        return mThumbIds;
    }


    /**
     *
     * references to our images
     */
    private Integer[] mThumbIds = {
            R.drawable.plugin,
            R.drawable.switcher,
            R.drawable.fridge,
            R.drawable.lamp,
            R.drawable.fan,
            R.drawable.phone,
            R.drawable.washmachine,
            R.drawable.stereo,
            R.drawable.food,
            R.drawable.computer,
            R.drawable.router,
            R.drawable.microwave,
            R.drawable.playstation,
    };}
