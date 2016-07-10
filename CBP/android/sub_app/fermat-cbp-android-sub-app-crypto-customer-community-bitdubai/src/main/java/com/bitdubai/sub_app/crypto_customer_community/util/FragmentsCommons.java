package com.bitdubai.sub_app.crypto_customer_community.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class FragmentsCommons {

    public static final String CONNECTION_RESULT = "connection_result";
    public static final int SEARCH_FILTER_OPTION_MENU_ID = 1;
    public static final int LOCATION_FILTER_OPTION_MENU_ID = 2;
    public static final int HELP_OPTION_MENU_ID = 3;
    public static final int CCC_BACKGROUND_TAB_ID = 4;
    public static final float CUSTOMER_IMAGES_CORNER_RADIUS = 50.0f;

    public static void setUpHeaderScreen(View headerView, Context activity, ActiveActorIdentityInformation identity) throws CantGetActiveLoginIdentityException {
        /**
         * Navigation view header
         */
        ImageView imageView = (ImageView) headerView.findViewById(R.id.ccc_image_view_profile);
        if (identity != null) {
            if (identity.getImage() != null) {
                if (identity.getImage().length > 0) {
                    imageView.setImageBitmap((BitmapFactory.decodeByteArray(identity.getImage(), 0, identity.getImage().length)));
                } else
                    Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
            } else
                Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
            FermatTextView fermatTextView = (FermatTextView) headerView.findViewById(R.id.ccc_txt_name);
            fermatTextView.setText(identity.getAlias());

            headerView.findViewById(R.id.ccc_image_view_profile_container).setVisibility(View.VISIBLE);
        }
    }
}
