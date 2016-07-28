package com.bitdubai.sub_app.crypto_customer_identity.common.holders;

import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_customer_identity.R;

/**
 * Created by nelson on 01/09/15.
 */

public class CryptoCustomerIdentityInfoViewHolder extends FermatViewHolder {
    private ImageView identityImage;
    private FermatTextView identityName;

    public CryptoCustomerIdentityInfoViewHolder(View itemView) {
        super(itemView);

        identityImage = (ImageView) itemView.findViewById(R.id.crypto_customer_identity_image);
        identityName = (FermatTextView) itemView.findViewById(R.id.crypto_customer_identity_alias);
    }

    public void setImage(byte[] imageInBytes) {
        /*
        ByteArrayInputStream bytes = new ByteArrayInputStream(imageInBytes);
        BitmapDrawable bmd = new BitmapDrawable(bytes);
        identityImage.setImageBitmap(bmd.getBitmap());
        */
    }

    public ImageView getImage() {
        return identityImage;
    }

    public void setText(String text) {
        identityName.setText(text);
    }

    public void setText(SpannableString text) {
        identityName.setText(text);
    }


}
