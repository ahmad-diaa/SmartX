package com.smartx.cookies.smartx;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<Integer> imgid;

    private ArrayList<String> tempItemname;
    private ArrayList<Integer> tempImgid;
    Button addRoom;

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Integer> imgid) {
        super(context, R.layout.mylist, itemname);
        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
        tempImgid = new ArrayList<Integer>();
        tempItemname = new ArrayList<String>();
        tempImgid.addAll(imgid);
        tempItemname.addAll(itemname);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.nameroom);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageroom);
        txtTitle.setText(itemname.get(position));
        imageView.setImageResource(imgid.get(position));
        return rowView;
    }

    ;

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        itemname.clear();

        for (int pos = 0; pos < tempItemname.size(); pos++) {
            String name = tempItemname.get(pos);
            if (name.startsWith(charText) || name.contains(" " + charText)) {
                itemname.add(name);
                imgid.add(tempImgid.get(pos));
            }
        }
        notifyDataSetChanged();
    }

}

