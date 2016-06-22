package com.bitdubai.sub_app.crypto_broker_community.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class FragmentsCommons {
    public static final String LOCAL_BROADCAST_CHANNEL = "sub.app.intra.user.community.broacast.chanel";
    public static final String BROADCAST_CONNECTED_UPDATE = "sub.app.intra.user.community.broacast.connected";
    public static final String BROADCAST_DISCONNECTED_UPDATE = "sub.app.intra.user.community.broacast.disconnected";
    public static final String FROM_ACTIONBAR_SEND_ICON_CONTACTS = "from_contacts";
    public static final String PRESENTATION_IDENTITY_CREATED = "identity_created";
    public static final String CREATE_EXTRA_USER = "create_extra_user";
    public static final String PRESENTATION_SCREEN_ENABLED = "presentation_screen_enabled";
    public static final String BACK_WITHOUT_IDENTITY = "back_without_identity";
    public static final int SELECT_IDENTITY = 0;

    public static void setUpHeaderScreen(View headerView, Context activity, ActiveActorIdentityInformation identity) throws CantGetActiveLoginIdentityException {
        /**
         * Navigation view header
         */
        ImageView imageView = (ImageView) headerView.findViewById(R.id.cbc_image_view_profile);
        if (identity != null) {
            if (identity.getImage() != null) {
                if (identity.getImage().length > 0) {
                    imageView.setImageBitmap((BitmapFactory.decodeByteArray(identity.getImage(), 0, identity.getImage().length)));
                } else
                    Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
            } else
                Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
            FermatTextView fermatTextView = (FermatTextView) headerView.findViewById(R.id.cbc_txt_name);
            fermatTextView.setText(identity.getAlias());

            headerView.findViewById(R.id.cbc_image_view_profile_container).setVisibility(View.VISIBLE);
        }
    }
}
