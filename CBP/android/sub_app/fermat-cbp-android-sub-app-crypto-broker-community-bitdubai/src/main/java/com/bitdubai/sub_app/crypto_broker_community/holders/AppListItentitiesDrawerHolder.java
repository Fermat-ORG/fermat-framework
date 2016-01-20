package com.bitdubai.sub_app.crypto_broker_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_broker_community.R;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
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
