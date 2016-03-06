package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPairDetail;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.EarningsOverviewViewHolder;

import java.util.List;

/**
 * Created by nelson on 27/01/16.
 */
public class EarningsOverviewAdapter extends FermatAdapter<EarningsPairDetail, EarningsOverviewViewHolder> {
    private Currency earningCurrency;
    private TimeFrequency frequency;

    public EarningsOverviewAdapter(Context context, List<EarningsPairDetail> dataSet, Currency earningCurrency) {
        super(context, dataSet);
        this.earningCurrency = earningCurrency;
        frequency = TimeFrequency.DAILY;
    }

    @Override
    protected EarningsOverviewViewHolder createHolder(View itemView, int type) {
        return new EarningsOverviewViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_earnings_list_item;
    }

    @Override
    protected void bindHolder(EarningsOverviewViewHolder holder, EarningsPairDetail data, int position) {
        holder.bind(data, frequency, earningCurrency);
    }

    public void setTimeFrecuency(TimeFrequency frequency){
        this.frequency = frequency;
    }
}
