package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatRoundedImageView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by josemanueldsds on 30/11/15.
 */
public class AppFriendsListHolder extends FermatViewHolder {
    public ImageView friendAvatar;
    public FermatTextView friendName;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AppFriendsListHolder(View itemView) {
        super(itemView);

        friendName = (FermatTextView) itemView.findViewById(R.id.username);
        friendAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
    }
}
