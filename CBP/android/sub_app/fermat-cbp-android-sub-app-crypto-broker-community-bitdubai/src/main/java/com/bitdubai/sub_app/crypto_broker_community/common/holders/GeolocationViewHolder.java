package com.bitdubai.sub_app.crypto_broker_community.common.holders;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.sub_app.crypto_broker_community.R;


/**
 * Created by nelsonalfo on 28/06/16.
 */
public class GeolocationViewHolder extends FermatViewHolder {

    private TextView country;
    private TextView state;


    public GeolocationViewHolder(View itemView, int holderType) {
        super(itemView, holderType);

        country = (TextView) itemView.findViewById(R.id.cbc_country_search);
        state = (TextView) itemView.findViewById(R.id.cbc_state_search);
    }

    public void bind(ExtendedCity data) {
        country.setText(data.getCountryName());
        state.setText(data.getName());
    }
}
