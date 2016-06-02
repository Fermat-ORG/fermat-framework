package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;

/**
 * @author Jose Manuel De Sousa.
 */
public class IssuerCommunityNotificationsHolder extends FermatViewHolder {

    public ImageView userAvatar;
    public FermatTextView userName;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public IssuerCommunityNotificationsHolder(View itemView) {
        super(itemView);
        userName = (FermatTextView) itemView.findViewById(R.id.username);
        userAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
    }
}
