package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.BrokerExchangeRatesViewHolder;

/**
 * Created by nelson on 24/11/15.
 */
public class BrokerExchangeRatesAdapter extends FermatAdapter<MerchandiseExchangeRate, BrokerExchangeRatesViewHolder> {

    public BrokerExchangeRatesAdapter(Context context) {
        super(context);
    }

    @Override
    protected BrokerExchangeRatesViewHolder createHolder(View itemView, int type) {
        return new BrokerExchangeRatesViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccw_broker_list_item_exchange_rate_subitem;
    }

    @Override
    protected void bindHolder(BrokerExchangeRatesViewHolder holder, MerchandiseExchangeRate data, int position) {
        holder.bind(data);
    }
}
