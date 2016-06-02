package org.fermat.fermat_dap_android_sub_app_asset_user_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

/**
 * @author Jose Manuel De Sousa
 */
public class UserListItentitiesDrawerHolder extends FermatViewHolder {

    public FermatTextView userIdentityName;
    public ImageView userIdentityImage;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    protected UserListItentitiesDrawerHolder(View itemView) {
        super(itemView);

        userIdentityImage = (ImageView) itemView.findViewById(R.id.imageView_avatar);
        userIdentityName = (FermatTextView) itemView.findViewById(R.id.textView_identity);

    }
}
