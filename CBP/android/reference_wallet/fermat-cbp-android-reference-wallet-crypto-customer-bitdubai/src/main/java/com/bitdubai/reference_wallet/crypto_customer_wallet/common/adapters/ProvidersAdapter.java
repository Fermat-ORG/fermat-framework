package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.ProviderViewHolder;

import java.util.List;

/**
 * Created by nelson on 31/12/15.
 */
public class ProvidersAdapter extends SingleDeletableItemAdapter<CurrencyExchangeRateProviderManager, ProviderViewHolder> {

    public ProvidersAdapter(Context context, List<CurrencyExchangeRateProviderManager> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ProviderViewHolder createHolder(View itemView, int type) {
        return new ProviderViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccw_wizard_recycler_view_item;
    }
}
