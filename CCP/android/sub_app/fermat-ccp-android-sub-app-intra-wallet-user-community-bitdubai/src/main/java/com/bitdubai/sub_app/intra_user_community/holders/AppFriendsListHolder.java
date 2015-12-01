package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatRoundedImageView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by josemanueldsds on 30/11/15.
 */
public class AppFriendsListHolder extends FermatViewHolder {
    public FermatRoundedImageView friendAvatar;
    public FermatTextView friendName;

    /**
     * Constructor
     *
     * @param itemView
     */
    protected AppFriendsListHolder(View itemView) {
        super(itemView);

        friendName = (FermatTextView) itemView.findViewById(R.id.username);
        friendAvatar = (FermatRoundedImageView) itemView.findViewById(R.id.user_avatar);
    }
}
