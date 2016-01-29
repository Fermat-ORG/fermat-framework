package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyPair;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.EarningsCurrencyPairsViewHolder;

import java.util.List;

/**
 * Created by nelson on 29/01/16.
 */
public class EarningsCurrencyPairsAdapter extends FermatAdapter<CurrencyPair, EarningsCurrencyPairsViewHolder> {
    private int selectedItem;

    public EarningsCurrencyPairsAdapter(Context context, List<CurrencyPair> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected EarningsCurrencyPairsViewHolder createHolder(View itemView, int type) {
        return new EarningsCurrencyPairsViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_earnings_currency_pair_item;
    }

    @Override
    protected void bindHolder(EarningsCurrencyPairsViewHolder holder, CurrencyPair data, int position) {
        holder.bind(data, selectedItem == position);
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
    }
}
