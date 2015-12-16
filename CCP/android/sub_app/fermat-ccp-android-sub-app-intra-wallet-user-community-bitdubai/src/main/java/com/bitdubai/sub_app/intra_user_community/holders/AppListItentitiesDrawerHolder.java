package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * @author Jose Manuel De Sousa
 */
public class AppListItentitiesDrawerHolder extends FermatViewHolder {

    public FermatTextView userIdentityName;
    public ImageView userIdentityImage;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    protected AppListItentitiesDrawerHolder(View itemView) {
        super(itemView);

        userIdentityImage = (ImageView) itemView.findViewById(R.id.imageView_avatar);
        userIdentityName = (FermatTextView)itemView.findViewById(R.id.textView_identity);

    }
}
