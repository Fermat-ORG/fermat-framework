package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.ContractDetailViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.ContractListViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.SingleChoiceViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.EmptyContractInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.EmptyCustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail.ContractDetailActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/01/16.
 */
public class ContractDetailAdapter extends FermatAdapter<ContractDetail, ContractDetailViewHolder> {

    public ContractDetailAdapter(Context context, List<ContractDetail> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ContractDetailViewHolder createHolder(View itemView, int type) {
        return new ContractDetailViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccw_contract_detail_item;
    }

    @Override
    protected void bindHolder(ContractDetailViewHolder holder, ContractDetail data, int position) {
        holder.bind(data);
    }
}

