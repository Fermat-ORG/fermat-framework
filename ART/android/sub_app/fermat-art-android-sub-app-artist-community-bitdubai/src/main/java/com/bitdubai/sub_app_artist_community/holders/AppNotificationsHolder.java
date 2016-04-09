package com.bitdubai.sub_app_artist_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.artist_community.R;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class AppNotificationsHolder extends FermatViewHolder {

    public ImageView userAvatar;
    public FermatTextView userName;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public AppNotificationsHolder(View itemView) {
        super(itemView);

        userName = (FermatTextView)itemView.findViewById(R.id.afc_user_name);
        userAvatar = (ImageView)itemView.findViewById(R.id.afc_imageView_avatar);

    }
}
