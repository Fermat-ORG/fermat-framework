package com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;




public class NavigationDrawerArrayAdapter extends ArrayAdapter<String>  {
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
        else
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.wallet_framework_activity_framework_navigation_drawer_row_layout, parent, false);

            TextView textView = (TextView) rowView.findViewById(R.id.label);

            textView.setTypeface(MyApplication.getDefaultTypeface(), 1);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText(values[position]);

            switch (position)
            {
                case 1:
                    imageView.setImageResource(R.drawable.ic_action_user);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.ic_action_accounts);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.ic_action_bank);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.ic_action_coupon);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.ic_action_discount);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.ic_action_voucher);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.ic_action_gift_card);
                    break;
                case 8:
                    imageView.setImageResource(R.drawable.ic_action_clone);
                    break;
                case 9:
                    imageView.setImageResource(R.drawable.ic_action_child);
                    break;
                case 10:
                    imageView.setImageResource(R.drawable.ic_action_exit);
                    break;
            }
        }
        return rowView;
    }
}