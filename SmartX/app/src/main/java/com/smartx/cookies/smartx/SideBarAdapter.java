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
    /**
     *Declaring Variable to Understand which View is being worked on,
     if the view under inflation is header or item.
     */
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    /**
     *String Array to store the passed titles of available activities to visit.
     */
    private String titles[];

    /**
     *Int Array to store the passed images corresponding to each title.
     */
    private int icons[];

    /**
     *String Resource for header View Name.
     */
    private String name;

    /**
     *int Resource for header View Logo Of Company.
     */
    private int profile;
    Context context;

    /**
     * Creating a ViewHolder which extends the RecyclerView View Holder.
     ViewHolder is used to store the inflated views in order to recycle them.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;
        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView name;

        /**
         * Constructor to create ViewHolder with given parameters.
         the appropriate view is set in accordance with the the view type as passed when the holder object is created
         * @param itemView
         * @param ViewType
         */
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

    /**
     * Constructor to create side bar with given parameters.
     *
     * @param titles options of activities within the side bar to visit.
     * @param icons images corresponding to each title.
     * @param name name of user.
     * @param profile logo of company.
     * @param context
     */

    SideBarAdapter(String titles[],int icons[],String name, int profile,Context context){
        this.titles = titles;
        this.icons = icons;
        this.name = name;
        this.profile = profile;
        this.context=context;
    }

    /**
     *This method is called when the ViewHolder is Created,
     In this method we inflate the item.xml layout if the viewType is Type_ITEM or else we inflate header.xml
     if the viewType is TYPE_HEADER and pass it to the view holder.
     *
     * @param parent
     * @param viewType
     * @return
     */

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

    /**
     * This method is called when an item in a row is needed to be displayed.
     *
     * @param holder shows which view type is being created for item row.
     * @param position shows position of item that is being constructed to be displayed.
     */
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

    /**
     * This method returns the number of items present in the list.
     *
     * @return
     */

    @Override
    public int getItemCount() {
        return titles.length+1;
    }

    /**
     * With the following method we check what type of view is being passed.
     *
     * @param position
     * @return
     */

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }
}
