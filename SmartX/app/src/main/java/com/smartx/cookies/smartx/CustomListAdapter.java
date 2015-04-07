package com.smartx.cookies.smartx;

import android.app.Activity;
import android.util.Log;
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

     Activity context;
    public  ArrayList<String> itemName;
    public  ArrayList<Integer> imgId;

    private ArrayList<String> tempItemname;
    private ArrayList<Integer> tempImgid;
    Button addRoom;

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Integer> imgid) {
        super(context, R.layout.mylist, itemname);
        this.context = context;
        this.itemName = itemname;
        this.imgId = imgid;
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
        txtTitle.setText(itemName.get(position));
        imageView.setImageResource(imgId.get(position));
        return rowView;
    }

    ;

    /**
     * Filter the list of rooms (itemName) matching a certain word
     *
     * @param charText string to filter with
     */

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        itemName.clear();
        imgId.clear();

        Log.i(tempItemname.size() + " " , "malo ");
        for (int pos = 0; pos < tempItemname.size(); pos++) {
            String name = tempItemname.get(pos).toLowerCase();
            if (name.startsWith(charText) || name.contains(" " + charText)) {
                itemName.add(tempItemname.get(pos));
                imgId.add(tempImgid.get(pos));
            }
        }
        notifyDataSetChanged();
    }

}

