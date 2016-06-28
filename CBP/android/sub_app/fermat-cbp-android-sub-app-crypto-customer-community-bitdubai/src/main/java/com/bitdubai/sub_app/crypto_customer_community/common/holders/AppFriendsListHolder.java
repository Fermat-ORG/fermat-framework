package com.bitdubai.sub_app.crypto_customer_community.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_customer_community.R;

/**
 * Created by Alejandro Bicelis on 04/02/2016.
 */
public class AppFriendsListHolder extends FermatViewHolder {

    public ImageView friendAvatar;
    public FermatTextView friendName;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public AppFriendsListHolder(View itemView) {
        super(itemView);

        friendName = (FermatTextView) itemView.findViewById(R.id.ccc_selectable_identity_username);
        friendAvatar = (ImageView) itemView.findViewById(R.id.ccc_selectable_identity_user_avatar);
    }
}
