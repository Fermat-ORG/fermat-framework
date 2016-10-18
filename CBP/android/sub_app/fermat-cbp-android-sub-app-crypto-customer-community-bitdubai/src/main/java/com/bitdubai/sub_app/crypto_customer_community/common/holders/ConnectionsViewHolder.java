package com.bitdubai.sub_app.crypto_customer_community.common.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;


/**
 * Created by Alejandro Bicelis on 04/02/2016.
 */
public class ConnectionsViewHolder extends FermatViewHolder {

    private final Resources res;
    private ImageView customerImage;
    private FermatTextView customerName;
    private FermatTextView customerLocation;
    private String placeAddress;
    private String countryAddress;

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
        if (data.getProfileStatus() != null && data.getProfileStatus().getCode().equalsIgnoreCase("OF"))
            customerName.setTextColor(Color.RED);
        else if (data.getProfileStatus() != null && data.getProfileStatus().getCode().equalsIgnoreCase("ON"))
            customerName.setTextColor(Color.parseColor("#3CD84E"));//VERDE NO BRILLANTE
        else if (data.getProfileStatus() == null || data.getProfileStatus().getCode().equalsIgnoreCase("UN"))
            customerName.setTextColor(Color.parseColor("#4d4d4d"));//BLACK);//res.getColor(R.color.color_black_light));

        if (data.getCountry().equals("null") || data.getCountry().equals("") || data.getCountry().equals("country"))
            countryAddress = "--";
        else countryAddress = data.getCountry();
        if (data.getPlace().equals("null") || data.getPlace().equals("") || data.getPlace().equals("country"))
            placeAddress = "--";
        else placeAddress = data.getPlace();
        customerLocation.setText(String.format("%s / %s", placeAddress, countryAddress));
        customerImage.setImageDrawable(getImgDrawable(data.getImage()));
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        Bitmap srcBitmap = customerImg != null && customerImg.length > 0 ?
                BitmapFactory.decodeByteArray(customerImg, 0, customerImg.length) :
                BitmapFactory.decodeResource(res, R.drawable.ic_profile_male);

        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(res, srcBitmap);
        bitmapDrawable.setCornerRadius(FragmentsCommons.CUSTOMER_IMAGES_CORNER_RADIUS);
        bitmapDrawable.setAntiAlias(true);

        return bitmapDrawable;
    }
}
