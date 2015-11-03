package com.bitdubai.android_core.app.common.version_1.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.identity.common.IdentityUserInformation;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUser;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NavigationDrawerArrayAdapter extends ArrayAdapter<String>  {
    private final Context context;
    private final List<String> values;


    private ImageView icon;
    private TextView userName;
    private ImageView imageView_intra_users;

    private IntraUserInformation activeIntraUser;


    public NavigationDrawerArrayAdapter(Context context, List<String> values,IntraUserInformation activeIntraUser) {
        super(context, R.layout.wallet_framework_activity_main_navigation_drawer_row_layout_empty, values);
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
        Typeface tf=Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        View rowView = convertView;
        try {
            if (position == 0) {


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                //testing



                rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_navigation_drawer_first_row, parent, false);

                icon = (ImageView) rowView.findViewById(R.id.icon);

                userName = (TextView) rowView.findViewById(R.id.label);

                imageView_intra_users = (ImageView) rowView.findViewById(R.id.icon_change_profile);


                if(activeIntraUser!=null) {
                    if (activeIntraUser.getProfileImage() != null) {
                        Bitmap bitmapDrawable = BitmapFactory.decodeByteArray(activeIntraUser.getProfileImage(), 0, activeIntraUser.getProfileImage().length);
                        icon.setImageBitmap(bitmapDrawable);
                    } else
                        Picasso.with(context).load(R.drawable.profile_image).into(imageView_intra_users);


                    TextView txtView_description = (TextView) rowView.findViewById(R.id.txtView_description);
                    if (txtView_description != null) {

                        txtView_description.setTypeface(tf, 1);

                        txtView_description.setText(activeIntraUser.getName());
                    }
                }


                ImageView iconEdit = (ImageView) rowView.findViewById(R.id.icon_change_profile);
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

                rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_framework_navigation_drawer_row_layout, parent, false);

                ImageView imageView = null;
                imageView = (ImageView) rowView.findViewById(R.id.icon);

                if(rowView.findViewById(R.id.label) != null) {
                    TextView textView = (TextView) rowView.findViewById(R.id.label);
                    textView.setTypeface(tf, 1);
                    textView.setText(values.get(position));
                }


                //if (ApplicationSession.getActivityId() == "DesktopActivity") {
                switch (position) {
                    case 1:
                        imageView.setImageResource(R.drawable.btn_drawer_home_active);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.btn_drawer_profile_normal);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.ic_action_wallet);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.ic_action_factory);
                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.btn_drawer_logout_normal);

                        break;

                    default:
                        imageView.setImageResource(R.drawable.unknown_icon);
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