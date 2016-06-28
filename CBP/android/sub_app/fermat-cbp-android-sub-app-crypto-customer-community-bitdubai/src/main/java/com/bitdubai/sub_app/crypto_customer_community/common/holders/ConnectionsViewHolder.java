package com.bitdubai.sub_app.crypto_customer_community.common.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.sub_app.crypto_customer_community.R;

/**
 * Created by Alejandro Bicelis on 04/02/2016.
 */
public class ConnectionsViewHolder extends FermatViewHolder {

    private final Resources res;
    private ImageView customerImage;
    private FermatTextView customerName;
    private FermatTextView customerLocation;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public ConnectionsViewHolder(View itemView, int type) {
        super(itemView, type);
        res = itemView.getResources();

        customerName = (FermatTextView) itemView.findViewById(R.id.ccc_customer_name);
        customerImage = (ImageView) itemView.findViewById(R.id.ccc_customer_image);
        customerLocation = (FermatTextView) itemView.findViewById(R.id.ccc_location_text);
    }

    public void bind(CryptoCustomerCommunityInformation data) {
        customerName.setText(data.getAlias());
        customerImage.setImageDrawable(getImgDrawable(data.getImage()));
        customerLocation.setText("-- / --");
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        Bitmap srcBitmap = customerImg != null && customerImg.length > 0 ?
                BitmapFactory.decodeByteArray(customerImg, 0, customerImg.length) :
                BitmapFactory.decodeResource(res, R.drawable.ic_profile_male);

        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(res, srcBitmap);
        bitmapDrawable.setCornerRadius(100.0f);
        bitmapDrawable.setAntiAlias(true);

        return bitmapDrawable;
    }
}
