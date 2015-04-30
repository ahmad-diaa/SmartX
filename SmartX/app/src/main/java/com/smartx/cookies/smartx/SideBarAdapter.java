package com.smartx.cookies.smartx;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *SE Sprint2
 *SideBarAdapter.java
 * Purpose: Creating a side bar.
 *
 * @author Amir
 */

public class SideBarAdapter extends RecyclerView.Adapter<SideBarAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private String titles[];
    private int icons[];
    private String name;
    private int profile;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;
        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView name;

        public ViewHolder(View itemView,int ViewType) {
            super(itemView);
            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                holderId = 1;
            }
            else{
                name = (TextView) itemView.findViewById(R.id.name);
                profile = (ImageView) itemView.findViewById(R.id.circleView);
                holderId = 0;
            }
        }
    }

    SideBarAdapter(String titles[],int icons[],String name, int profile,Context context){
        this.titles = titles;
        this.icons = icons;
        this.name = name;
        this.profile = profile;
        this.context=context;
    }

    @Override
    public SideBarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder( SideBarAdapter.ViewHolder holder, int position) {
        if(holder.holderId ==1) {
            holder.textView.setText(titles[position - 1]);
            holder.imageView.setImageResource(icons[position -1]);
        }
        else{
            holder.profile.setImageResource(profile);
            holder.name.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        return titles.length+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }
}
