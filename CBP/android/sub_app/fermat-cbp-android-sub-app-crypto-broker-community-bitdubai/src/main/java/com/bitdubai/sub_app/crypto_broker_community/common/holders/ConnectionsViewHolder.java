package com.bitdubai.sub_app.crypto_broker_community.common.holders;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityExtraData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.sub_app.crypto_broker_community.R;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */

public class ConnectionsViewHolder extends FermatViewHolder {

    private final Resources res;
    private ImageView brokerImage;
    private FermatTextView brokerName;
    private FermatTextView brokerLocation;
    private FermatTextView merchandises;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public ConnectionsViewHolder(View itemView, int type) {
        super(itemView, type);
        res = itemView.getResources();

        brokerName = (FermatTextView) itemView.findViewById(R.id.cbc_broker_name);
        brokerImage = (ImageView) itemView.findViewById(R.id.cbc_broker_image);
        brokerLocation = (FermatTextView) itemView.findViewById(R.id.cbc_location_text);
        merchandises = (FermatTextView) itemView.findViewById(R.id.cbc_merchandises);
    }

    public void bind(CryptoBrokerCommunityInformation data) {

        brokerName.setText(data.getAlias());
        if (data.getProfileStatus() != null && data.getProfileStatus().getCode().equalsIgnoreCase("OF"))
            brokerName.setTextColor(Color.RED);
        else if (data.getProfileStatus() != null && data.getProfileStatus().getCode().equalsIgnoreCase("ON"))
            brokerName.setTextColor(Color.parseColor("#3CD84E"));//VERDE NO BRILLANTE
        else if (data.getProfileStatus() == null || data.getProfileStatus().getCode().equalsIgnoreCase("UN"))
            brokerName.setTextColor(Color.parseColor("#4d4d4d"));//BLACK);//res.getColor(R.color.color_black_light));

        String countryAddress;
        if (data.getCountry().equals("null") || data.getCountry().equals("") || data.getCountry().equals("country"))
            countryAddress = "--";

        else countryAddress = data.getCountry();
        String placeAddress;
        if (data.getPlace().equals("null") || data.getPlace().equals("") || data.getPlace().equals("country"))
            placeAddress = "--";
        else placeAddress = data.getPlace();
        brokerLocation.setText(String.format("%s / %s", placeAddress, countryAddress));
        brokerImage.setImageDrawable(getImgDrawable(data.getImage()));

        final CryptoBrokerIdentityExtraData brokerExtraData = data.getCryptoBrokerIdentityExtraData();
        if (brokerExtraData != null) {
            String extraText = brokerExtraData.getExtraText();
            extraText = (extraText != null && !extraText.isEmpty()) ? extraText : "Not Available Merchandises.";
            merchandises.setText(extraText);
        } else
            merchandises.setText("Not Available Merchandises.");
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.ic_profile_male);
    }
}
