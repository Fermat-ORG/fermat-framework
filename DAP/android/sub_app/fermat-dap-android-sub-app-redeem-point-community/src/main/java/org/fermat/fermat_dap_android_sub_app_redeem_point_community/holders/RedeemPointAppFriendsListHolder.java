package org.fermat.fermat_dap_android_sub_app_redeem_point_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;


/**
 * Jinmy Bohorquez 13/02/2016
 */
public class RedeemPointAppFriendsListHolder extends FermatViewHolder {

    public ImageView friendAvatar;
    public FermatTextView friendName;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public RedeemPointAppFriendsListHolder(View itemView) {
        super(itemView);
        friendName = (FermatTextView) itemView.findViewById(R.id.username);
        friendAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
    }
}
