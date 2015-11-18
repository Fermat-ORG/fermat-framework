package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.ContractBasicInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.ContractListViewHolder;

import java.util.List;

/**
 * Created by nelson on 18/11/15.
 */
public class ContractHistoryAdapter extends FermatAdapter<ContractBasicInformation, ContractListViewHolder> {

    public ContractHistoryAdapter(Context context) {
        super(context);
    }

    public ContractHistoryAdapter(Context context, List<ContractBasicInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ContractListViewHolder createHolder(View itemView, int type) {
        return new ContractListViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_contract_history_list_item;
    }

    @Override
    protected void bindHolder(ContractListViewHolder holder, ContractBasicInformation data, int position) {
        holder.bind(data);
    }
}
