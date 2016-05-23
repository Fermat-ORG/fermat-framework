package com.bitdubai.sub_app.fan_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.fan_community.R;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class AppFriendsListHolder extends FermatViewHolder {

    public ImageView friendAvatar;
    public FermatTextView friendName;
    public ImageView actorIcon;

    /**
     * Default constructor.
     * @param itemView cast ui elements
     */
    public AppFriendsListHolder(View itemView) {
        super(itemView);
        friendName = (FermatTextView) itemView.findViewById(R.id.afc_selectable_identity_username);
        friendAvatar = (ImageView) itemView.findViewById(R.id.afc_selectable_identity_user_avatar);
        actorIcon = (ImageView) itemView.findViewById(R.id.afc_recycler_icon);
    }
}
