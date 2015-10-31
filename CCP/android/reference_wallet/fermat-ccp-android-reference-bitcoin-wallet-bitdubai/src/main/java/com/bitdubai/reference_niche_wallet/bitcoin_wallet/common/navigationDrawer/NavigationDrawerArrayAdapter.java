package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigationDrawer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.identity.common.IdentityUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

import java.util.List;


public class NavigationDrawerArrayAdapter extends ArrayAdapter<String>  {
    private final Context context;
    private final List<String> values;


    private ImageView icon;
    private TextView userName;
    private ImageView imageView_intra_users;


    public NavigationDrawerArrayAdapter(Context context, List<String> values) {
        super(context, R.layout.navigation_drawer_base, values);
        try {

            this.context = context;
            this.values = values;
        }
        catch (Exception e) {
            throw e;
        }

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Typeface tf=Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        View rowView = convertView;
        try
        {
            if (position == 0) {


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                //testing



                rowView = inflater.inflate(R.layout.navigation_drawer_first_row, parent, false);

                icon = (ImageView) rowView.findViewById(R.id.icon);

                userName = (TextView) rowView.findViewById(R.id.label);

                imageView_intra_users = (ImageView) rowView.findViewById(R.id.imageView_change_user);



                /*switch (ApplicationSession.getActivityId())
                {
                    case "DesktopActivity":
                        rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_navigation_drawer_first_row, parent, false);
                        break;
                      default:
                        rowView = inflater.inflate(R.layout.wallet_manager_main_activity_navigation_drawer_first_row_empty, parent, false);
                          break;

                }*/
                //TextView txtView_description = (TextView) rowView.findViewById(R.id.txtView_description);
                if(userName != null){

                    userName.setTypeface(tf, 1);

                    //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                    userName.setText("Tessa Crankston");
                }


                ImageView iconEdit = (ImageView) rowView.findViewById(R.id.imageView_change_user);
                iconEdit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"cambiando de ususario proximamente",Toast.LENGTH_SHORT).show();

                    }
                });

            }
            else {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                rowView = inflater.inflate(R.layout.navigation_drawer_row, parent, false);

                ImageView imageView = null;
                imageView = (ImageView) rowView.findViewById(R.id.icon);
                if(rowView.findViewById(R.id.label) != null) {
                    TextView textView = (TextView) rowView.findViewById(R.id.label);
                    textView.setTypeface(tf, 1);
                    textView.setText(values.get(position));
                }




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
                        imageView.setImageResource(R.drawable.ic_action_exit);

                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.ic_action_wallet);
                        break;

                    case 6:
                        imageView.setImageResource(R.drawable.ic_action_wallet_published);
                        break;
                    default:
                        imageView.setImageResource(R.drawable.mati_profile);
                }



            }

            //}
        }
        catch (Exception e){
            throw e;
        }


        return rowView;
    }

    public void changeUser(IdentityUserInformation intraUserInformation){
        icon.setImageBitmap(BitmapFactory.decodeByteArray(intraUserInformation.getProfileImage(),0,intraUserInformation.getProfileImage().length));
        userName.setText(intraUserInformation.getName());
    }
    public void setIntraUsers(List<IntraUserInformation> list){

    }

}