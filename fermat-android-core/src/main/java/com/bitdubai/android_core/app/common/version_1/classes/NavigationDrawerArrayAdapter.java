package com.bitdubai.android_core.app.common.version_1.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat.R;


public class NavigationDrawerArrayAdapter extends ArrayAdapter<String>  {
    private final Context context;
    private final String[] values;


    public NavigationDrawerArrayAdapter(Context context, String[] values) {
        super(context, R.layout.wallet_framework_activity_framework_navigation_drawer_row_layout, values);
        try
        {

            this.context = context;
            this.values = values;
        }
        catch (Exception e)
        {
            throw e;
        }

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;
        try
        {
            if (position == 0)
            {


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                switch (MyApplication.getActivityId())
                {
                    case "DesktopActivity":
                        rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_navigation_drawer_first_row, parent, false);
                        break;
                    case "AdultsActivity":
                        rowView = inflater.inflate(R.layout.wallet_framework_activity_framework_navigation_drawer_first_row, parent, false);
                        break;
                    case "ShopActivity":
                        rowView = inflater.inflate(R.layout.shell_shop_desktop_activity_navigation_drawer_first_row, parent, false);
                        break;
                    default:
                        rowView = inflater.inflate(R.layout.wallet_manager_main_activity_navigation_drawer_first_row_empty, parent, false);

                }

                if(rowView.findViewById(R.id.label) != null){
                    TextView textView = (TextView) rowView.findViewById(R.id.label);

                    textView.setTypeface(MyApplication.getDefaultTypeface(), 1);

                    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                    textView.setText("Tessa Crankston");
                }


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

                switch (MyApplication.getActivityId()) {
                    case "DesktopActivity":
                        rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_framework_navigation_drawer_row_layout, parent, false);
                        break;
                    case "AdultsActivity":
                        rowView = inflater.inflate(R.layout.wallet_framework_activity_framework_navigation_drawer_row_layout, parent, false);
                        break;
                    case "ShopActivity":
                        rowView = inflater.inflate(R.layout.shell_shop_desktop_activity_navigation_drawer_row_layout, parent, false);
                        break;
                    case "PublisherActivity":
                        rowView = inflater.inflate(R.layout.wallet_framework_activity_framework_navigation_drawer_row_layout, parent, false);
                        break;
                    default:
                        rowView = inflater.inflate(R.layout.wallet_framework_activity_main_navigation_drawer_row_layout_empty, parent, false);
                        break;

                }
                ImageView imageView = null;
                if(rowView.findViewById(R.id.label) != null)
                {
                    TextView textView = (TextView) rowView.findViewById(R.id.label);
                    textView.setTypeface(MyApplication.getDefaultTypeface(), 1);
                    imageView = (ImageView) rowView.findViewById(R.id.icon);
                    textView.setText(values[position]);
                }


                if (MyApplication.getActivityId() == "DesktopActivity") {

                    switch (position) {
                        case 1:
                            imageView.setImageResource(R.drawable.ic_action_store);
                            break;
                        case 2:
                            imageView.setImageResource(R.drawable.ic_action_wallet);
                            break;
                        case 3:
                            imageView.setImageResource(R.drawable.ic_action_factory);
                            break;
                        case 4:
                            imageView.setImageResource(R.drawable.ic_action_wallet_published);
                            break;
                        case 5:
                            imageView.setImageResource(R.drawable.ic_action_wallet);
                            break;

                        case 6:
                            imageView.setImageResource(R.drawable.ic_action_exit);
                            break;
                    }

                }else if (MyApplication.getActivityId() == "PublisherActivity"){
                    switch (position)
                    {
                        case 1:
                            imageView.setImageResource(R.drawable.ic_action_store);
                            break;
                    }

                }else{

             /*   switch (position)
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
                }*/
                }

            }
        }
        catch (Exception e)
        {
            throw e;
        }


        return rowView;
    }
}