package com.smartx.cookies.smartx;

/**
 * Created by ahmad on 3/20/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceListAdapter extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] device_name;
    private final String[] device_type;

    public DeviceListAdapter(Activity context, String[] device_name, String [] device_type) {
        super(context, R.layout.mylist, device_name);
        // TODO Auto-generated constructor stub

        this.context=context;
        //this.device_name=device_name;
        this.device_name = new String[1];
        device_name[0] = "TV";
        this.device_type=device_type;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null ,true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.deviceName);

        txtTitle.setText(device_name[position]);
        return rowView;

    };
}