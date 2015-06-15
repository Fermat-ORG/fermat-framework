package com.bitdubai.sub_app.wallet_publisher.common.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.sub_app.wallet_publisher.R;

/**
 * Created by Natalia on 27/04/2015.
 */
public class NavigationDrawerArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;


    public NavigationDrawerArrayAdapter(Context context, String[] values) {
        super(context, R.layout.wallet_framework_activity_framework_navigation_drawer_row_layout, values);
        this.context = context;
        this.values = values;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;

        if (position == 0)
        {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.wallet_framework_activity_framework_navigation_drawer_first_row, parent, false);


            TextView textView = (TextView) rowView.findViewById(R.id.label);

            textView.setTypeface(MyApplication.getDefaultTypeface(), 1);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText("Tessa Crankston");

            //        ImageView iconEdit = (ImageView) rowView.findViewById(R.id.icon_edit_profile);
            //        iconEdit.setOnClickListener(new View.OnClickListener() {

            //            @Override
            //            public void onClick(View v) {
            //                Intent intent;

            //            }
            //       });

        }
        else {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.wallet_framework_activity_framework_navigation_drawer_row_layout, parent, false);
             rowView = inflater.inflate(R.layout.wallet_framework_activity_framework_navigation_drawer_row_layout, parent, false);


            }
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            textView.setTypeface(MyApplication.getDefaultTypeface(), 1);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText(values[position]);


             imageView.setImageResource(R.drawable.ic_action_store);


        return rowView;
    }
}