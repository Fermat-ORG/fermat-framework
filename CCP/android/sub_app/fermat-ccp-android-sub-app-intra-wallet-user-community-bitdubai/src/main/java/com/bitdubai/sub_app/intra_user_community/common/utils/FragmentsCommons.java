package com.bitdubai.sub_app.intra_user_community.common.utils;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.sub_app.intra_user_community.R;
import com.squareup.picasso.Picasso;

/**
 * Created by mati on 2015.11.12..
 */
public class FragmentsCommons {


    public static View setUpHeaderScreen(LayoutInflater inflater,Activity activity,IntraUserLoginIdentity intraUserLoginIdentity) throws CantGetActiveLoginIdentityException {
        /**
         * Navigation view header
         */
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        relativeLayout.setLayoutParams(layoutParams);
        View view = inflater.inflate(R.layout.intra_user_community_navigation_first_row, relativeLayout, true);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
        if(intraUserLoginIdentity!=null) {
            if (intraUserLoginIdentity.getProfileImage() != null) {
                if (intraUserLoginIdentity.getProfileImage().length > 0) {
                    imageView.setImageBitmap((BitmapFactory.decodeByteArray(intraUserLoginIdentity.getProfileImage(), 0, intraUserLoginIdentity.getProfileImage().length)));
                } else
                    Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
            }
            FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
            fermatTextView.setText(intraUserLoginIdentity.getAlias());
        }

        return view;

    }
}
