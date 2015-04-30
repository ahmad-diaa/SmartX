package com.smartx.cookies.smartx;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author maggiemoheb
 */
public class CustomListDevice extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> itemName;
    private ArrayList<String> tempItemname;
    Button addRoom;

    public ArrayList<String> getItemName() {
        return itemName;
    }

    public CustomListDevice(Activity context, ArrayList<String> itemName) {
        super(context, R.layout.activity_device_list, itemName);
        this.context = context;
        this.itemName = itemName;
        tempItemname = new ArrayList<String>();
        tempItemname.addAll(itemName);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.nameroom);
        txtTitle.setText(itemName.get(position));
        return rowView;
    }
    public void filter2(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        itemName.clear();
        for (int pos = 0; pos < tempItemname.size(); pos++) {
            String name = tempItemname.get(pos).toLowerCase();
            if (name.startsWith(charText) || name.contains(" " + charText)) {
                itemName.add(tempItemname.get(pos));
            }
        }
        notifyDataSetChanged();
    }

}
