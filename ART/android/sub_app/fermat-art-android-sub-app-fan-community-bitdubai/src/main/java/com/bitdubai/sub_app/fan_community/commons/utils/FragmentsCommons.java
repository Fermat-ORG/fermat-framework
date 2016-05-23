package com.bitdubai.sub_app.fan_community.commons.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.sub_app.fan_community.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class FragmentsCommons {

    public static View setUpHeaderScreen(
            LayoutInflater inflater,
            Context activity,
            ActiveActorIdentityInformation identity) throws CantGetActiveLoginIdentityException {
        /**
         * Navigation view header
         */
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 180);
        relativeLayout.setLayoutParams(layoutParams);
        View view = inflater.inflate(
                R.layout.afc_row_navigation_drawer_community_header,
                relativeLayout,
                true);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
        if (identity != null) {
            if (identity.getImage() != null) {
                if (identity.getImage().length > 0) {
                    imageView.setImageBitmap((
                            BitmapFactory.decodeByteArray(
                                    identity.getImage(),
                                    0,
                                    identity.getImage().length)));
                } else
                    Picasso.with(activity).load(R.drawable.afc_profile_image).into(imageView);
            } else
                Picasso.with(activity).load(R.drawable.afc_profile_image).into(imageView);
            FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
            fermatTextView.setText(identity.getAlias());
        }
        return view;
    }
}

