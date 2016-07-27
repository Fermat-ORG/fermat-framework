package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.EarningsWizardViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningsWizardData;

import java.util.List;

/**
 * Created by nelson on 31/12/15.
 */
public class EarningsWizardAdapter extends FermatAdapter<EarningsWizardData, EarningsWizardViewHolder> {

    public EarningsWizardAdapter(Context context, List<EarningsWizardData> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected EarningsWizardViewHolder createHolder(View itemView, int type) {
        return new EarningsWizardViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_wizard_earnings_item;
    }

    @Override
    protected void bindHolder(EarningsWizardViewHolder holder, EarningsWizardData data, int position) {
        holder.bind(data);
    }
}
