package com.bitdubai.sub_app.crypto_customer_community.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.holders.GeolocationViewHolder;

import java.util.List;


/**
 * Created by roy on 13/06/16.
 * Update by Jose Cardozo josejcb (josejcb89@gmail.com) on 27/06/16.
 */
public class GeolocationAdapter extends FermatAdapter<ExtendedCity, GeolocationViewHolder> {

    public GeolocationAdapter(Context context, List<ExtendedCity> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected GeolocationViewHolder createHolder(View itemView, int type) {
        return new GeolocationViewHolder(itemView, type);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccc_dialog_geolocation_browser_results_item;
    }

    @Override
    protected void bindHolder(GeolocationViewHolder holder, ExtendedCity data, int position) {
        holder.bind(data);
    }
}
