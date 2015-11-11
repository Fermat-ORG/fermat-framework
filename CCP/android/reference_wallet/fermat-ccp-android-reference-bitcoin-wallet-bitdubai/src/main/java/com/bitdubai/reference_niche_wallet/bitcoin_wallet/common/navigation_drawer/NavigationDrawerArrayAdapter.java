package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.identity.common.IdentityUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NavigationDrawerArrayAdapter extends ArrayAdapter<String>  {
    private final Context context;
    private final List<String> values;


    private ImageView icon;
    private TextView userName;
    private ImageView imageView_intra_users;

    private IntraUserLoginIdentity activeIntraUser;


    public NavigationDrawerArrayAdapter(Context context, List<String> values,IntraUserLoginIdentity activeIntraUser) {
        super(context, R.layout.navigation_drawer_base, values);
        this.activeIntraUser = activeIntraUser;
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
        Typeface tf=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        View rowView = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            if (position == 0) {


                rowView = inflater.inflate(R.layout.navigation_drawer_row_first, parent, false);

                icon = (ImageView) rowView.findViewById(R.id.imageView_profile);



                userName = (TextView) rowView.findViewById(R.id.label);

                imageView_intra_users = (ImageView) rowView.findViewById(R.id.imageView_change_user);


                if(userName != null){
                    userName.setTypeface(tf, 1);
                    userName.setText(activeIntraUser.getAlias());
                }

                if(activeIntraUser.getProfileImage()!=null){
                    if(activeIntraUser.getProfileImage().length>0)
                    icon.setImageBitmap(BitmapFactory.decodeByteArray(activeIntraUser.getProfileImage(),0,activeIntraUser.getProfileImage().length));
                    else icon.setImageResource(R.drawable.profile_image);
                }else{
                    icon.setImageResource(R.drawable.profile_image);
                }



            }
            else {

                rowView = inflater.inflate(R.layout.navigation_row, parent, false);

                ImageView imageView = null;
                imageView = (ImageView) rowView.findViewById(R.id.imageView_icon);
                TextView textView = (TextView) rowView.findViewById(R.id.textView_label);
                if(textView != null) {
                    textView.setTypeface(tf, 1);
                    textView.setText(values.get(position));
                }




                switch (position) {
                    case 1:
                        imageView.setImageResource(R.drawable.btn_drawer_home_normal);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.btn_drawer_profile_normal);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.btn_drawer_request_normal);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.btn_drawer_settings_normal);

                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.btn_drawer_logout_normal);
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