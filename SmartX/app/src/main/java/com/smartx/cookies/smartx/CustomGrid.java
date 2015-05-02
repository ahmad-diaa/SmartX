package com.smartx.cookies.smartx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGrid extends BaseAdapter{
    private Context mContext;
    private final String[] plugNames;
    private final int[] plugPhotos;

    public CustomGrid(Context c, String[] names, int[] photos) {
        mContext = c;
        this.plugPhotos = photos;
        this.plugNames = names;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return plugNames.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid.setLayoutParams(new GridView.LayoutParams(150, 150));
            grid.setPadding(50, 50, 50, 50);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(plugNames[position]);
            imageView.setImageResource(plugPhotos[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}