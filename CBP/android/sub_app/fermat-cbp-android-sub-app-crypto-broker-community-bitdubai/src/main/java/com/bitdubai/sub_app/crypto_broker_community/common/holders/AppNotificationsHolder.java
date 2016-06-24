package com.bitdubai.sub_app.crypto_broker_community.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatRoundedImageView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_broker_community.R;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AppNotificationsHolder extends FermatViewHolder {

    public FermatRoundedImageView userAvatar;
    public FermatTextView userName;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public AppNotificationsHolder(View itemView) {
        super(itemView);

        userName = (FermatTextView)itemView.findViewById(R.id.userName);
        userAvatar = (FermatRoundedImageView)itemView.findViewById(R.id.imageView_avatar);

    }
}
