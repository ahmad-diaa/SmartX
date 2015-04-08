package com.smartx.cookies.smartx;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<Integer> imgid;
    Button addRoom;

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Integer> imgid) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.nameroom);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageroom);
        txtTitle.setText(itemname.get(position));
        int i = imgid.get(position);
        imageView.setImageResource(imgid.get(position));
        return rowView;
    }

    ;
}

