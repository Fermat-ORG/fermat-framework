package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.EarningsOverviewViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningTestData;

import java.util.List;

/**
 * Created by nelson on 27/01/16.
 */
public class EarningsOverviewAdapter extends FermatAdapter<EarningTestData, EarningsOverviewViewHolder> {


    public EarningsOverviewAdapter(Context context, List<EarningTestData> dataSet) {
        super(context, dataSet);
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
    protected void bindHolder(EarningsOverviewViewHolder holder, EarningTestData data, int position) {
        holder.bind(data);
    }
}
