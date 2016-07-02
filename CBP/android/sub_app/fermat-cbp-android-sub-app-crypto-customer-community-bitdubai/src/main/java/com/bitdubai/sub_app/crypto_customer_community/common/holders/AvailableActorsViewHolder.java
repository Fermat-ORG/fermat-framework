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
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AvailableActorsViewHolder extends FermatViewHolder {

    private ImageView customerImage;
    private ImageView connectionState;
    private FermatTextView customerName;
    private FermatTextView customerLocation;
    private FermatTextView connectionText;

    private Resources res;


    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     * @param type     the view older type ID
     */
    public AvailableActorsViewHolder(View itemView, int type) {
        super(itemView, type);
        res = itemView.getResources();

        customerImage = (ImageView) itemView.findViewById(R.id.ccc_customer_image);
        connectionState = (ImageView) itemView.findViewById(R.id.ccc_status_icon);
        customerName = (FermatTextView) itemView.findViewById(R.id.ccc_customer_name);
        customerLocation = (FermatTextView) itemView.findViewById(R.id.ccc_location_text);
        connectionText = (FermatTextView) itemView.findViewById(R.id.ccc_connection_text);
    }

    public void bind(CryptoCustomerCommunityInformation data, CryptoCustomerCommunitySubAppModuleManager moduleManager) {
        if (data.getConnectionState() != null) {
            switch (data.getConnectionState()) {
                case CONNECTED:
                    connectionState.setImageResource(R.drawable.ccc_added_contact);
                    connectionText.setText(R.string.ccc_connection_state_connected);
                    connectionText.setVisibility(View.VISIBLE);
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    connectionState.setImageResource(R.drawable.ccc_add_contact);
                    connectionText.setText(R.string.ccc_connection_state_pending_acceptance);
                    connectionText.setVisibility(View.VISIBLE);
                case PENDING_LOCALLY_ACCEPTANCE:
                    connectionState.setImageResource(R.drawable.ccc_add_contact);
                    connectionText.setText(R.string.ccc_connection_state_pending_acceptance);
                    connectionText.setVisibility(View.VISIBLE);
                default:
                    connectionState.setImageResource(R.drawable.ccc_add_contact);
                    connectionText.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        customerName.setText(data.getAlias());
        customerImage.setImageDrawable(getImgDrawable(data.getImage()));

        final Address address = getAddress(moduleManager, data.getLocation());
        String locationText = "-- / --";
        if (address != null) {
            String place = address.getCity().equals("null") ? address.getCounty() : address.getCity();
            locationText = address.getCountry() + " / " + place;
        }

        customerLocation.setText(locationText);
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

    private Address getAddress(CryptoCustomerCommunitySubAppModuleManager moduleManager, Location location) {
        if (location.getLatitude() == 0 && location.getLongitude() == 0)
            return null;

        try {
            return moduleManager.getAddressByCoordinate(location.getLatitude().floatValue(), location.getLongitude().floatValue());
        } catch (CantCreateAddressException e) {
            return null;
        }
    }
}
