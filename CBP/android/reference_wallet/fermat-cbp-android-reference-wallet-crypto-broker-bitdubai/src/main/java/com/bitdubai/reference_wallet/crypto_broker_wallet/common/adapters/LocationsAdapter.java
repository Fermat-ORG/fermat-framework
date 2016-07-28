package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.LocationViewHolder;

import java.util.List;

/**
 * Created by nelson on 30/12/15.
 */
public class LocationsAdapter extends SingleDeletableItemAdapter<String, LocationViewHolder> {

    public LocationsAdapter(Context context, List dataSet) {
        super(context, dataSet);
    }

    @Override
    protected LocationViewHolder createHolder(View itemView, int type) {
        return new LocationViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {

        return R.layout.cbw_wizard_recycler_view_locations_item;
    }
}
