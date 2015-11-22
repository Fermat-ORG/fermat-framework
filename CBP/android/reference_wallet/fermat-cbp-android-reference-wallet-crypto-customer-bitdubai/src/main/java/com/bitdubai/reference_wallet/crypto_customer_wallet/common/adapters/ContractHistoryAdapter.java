package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.ContractListViewHolder;

import java.util.List;

/**
 * Created by nelson on 21/11/15.
 */
public class ContractHistoryAdapter extends FermatAdapter<ContractBasicInformation, ContractListViewHolder> {

    public ContractHistoryAdapter(Context context, List<ContractBasicInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ContractListViewHolder createHolder(View itemView, int type) {
        return new ContractListViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccw_contract_history_list_item;
    }

    @Override
    protected void bindHolder(ContractListViewHolder holder, ContractBasicInformation data, int position) {
        holder.bind(data);
    }
}
