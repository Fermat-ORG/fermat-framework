package com.bitdubai.sub_app.crypto_broker_identity.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_broker_identity.R;

/**
 * Created by nelson on 01/09/15.
 */
public class IdentityInfoViewHolder extends FermatViewHolder {
    private ImageView identityImage;
    private FermatTextView identityName;

    public IdentityInfoViewHolder(View itemView) {
        super(itemView);

        identityImage = (ImageView) itemView.findViewById(R.id.identity_image);
        identityName = (FermatTextView) itemView.findViewById(R.id.identity_alias);
    }

    public ImageView getIdentityImage() {
        return identityImage;
    }

    public FermatTextView getIdentityName() {
        return identityName;
    }
}
