package com.bitdubai.sub_app.crypto_customer_community.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_customer_community.R;

/**
 * Created by Alejandro Bicelis on 02/02/2016.
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

        userName = (FermatTextView)itemView.findViewById(R.id.ccc_userName);
        userAvatar = (ImageView)itemView.findViewById(R.id.ccc_imageView_avatar);

    }
}
