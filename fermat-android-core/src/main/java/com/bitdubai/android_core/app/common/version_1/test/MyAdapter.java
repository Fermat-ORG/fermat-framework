package com.bitdubai.android_core.app.common.version_1.test;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;

import java.util.List;

/**
 * Created by hp1 on 28-12-2014.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
 
    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
                                               // IF the viaew under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;
    private final List<MenuItem> values;
    private final IntraUserLoginIdentity activeIntraUser;
    private final NavigationDrawerFragment.NavigationDrawerCallbacks navigationViewCallback;
    private String name;        //String Resource for header View Name
    Context context;
 
 
    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them
 
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;
 
        TextView textView;
        ImageView imageView;
        TextView Name;
        Context contxt;
 
 
        public ViewHolder(View itemView,int ViewType,Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
             contxt = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created
 
            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.label); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.icon);// Creating ImageView object with the id of ImageView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else{
 
 
                Name = (TextView) itemView.findViewById(R.id.label);         // Creating Text View object from header.xml for name
//                email = (TextView) itemView.findViewById(R.id.email);       // Creating Text View object from header.xml for email
//                profile = (ImageView) itemView.findViewById(R.id.circleView);// Creating Image view object from header.xml for profile pic
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
 
 
 
        }
 
 
        @Override
        public void onClick(View v) {

            navigationViewCallback.onNavigationDrawerItemSelected(getPosition(),values.get(getPosition()).getLinkToActivity().getCode());
 
        }
    }
 


    public MyAdapter(Context passedContext,  List<MenuItem> values,IntraUserLoginIdentity activeIntraUser,NavigationDrawerFragment.NavigationDrawerCallbacks navigationViewCallback){ // MyAdapter Constructor with titles and icons parameter
        // titles, icons, name, email, profile pic are passed from the main activity as we
        //here we assign those passed values to the values we declared here
        this.values = values;
        this.activeIntraUser = activeIntraUser;
        this.context = passedContext;
        name = "matias";
        this.navigationViewCallback = navigationViewCallback;
        //in adapter



    }
 
 
 
    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder
 
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_manager_desktop_activity_framework_navigation_drawer_row_layout,parent,false); //Inflating the layout
 
            ViewHolder vhItem = new ViewHolder(v,viewType,context); //Creating ViewHolder and passing the object of type view
 
            return vhItem; // Returning the created object
 
            //inflate your layout and pass it to view holder
 
        } else if (viewType == TYPE_HEADER) {
 
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_manager_desktop_activity_navigation_drawer_first_row,parent,false); //Inflating the layout
 
            ViewHolder vhHeader = new ViewHolder(v,viewType,context); //Creating ViewHolder and passing the object of type view
 
            return vhHeader; //returning the object created
 
 
        }
        return null;
 
    }
 
    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
                                                              // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(values.get(position - 1).getLabel()); // Setting the Text with the array of our Titles
            switch (position){
                case 1:
                    holder.imageView.setImageResource(R.drawable.ic_action_about);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.ic_action_add_alarm);
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.mati_profile);
                    break;
            }
//            holder.imageView.setImageResource(mIcons[position -1]);// Settimg the image with array of our icons
        }
        else{
 
//            holder.profile.setImageResource(profile);           // Similarly we set the resources for header view
            holder.Name.setText(name);
//            holder.email.setText(email);
        }
    }
 
    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return values.size()+1; // the number of items in the list will be +1 the titles including the header view.
    }
 
 
    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
 
        return TYPE_ITEM;
    }
 
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
 
}