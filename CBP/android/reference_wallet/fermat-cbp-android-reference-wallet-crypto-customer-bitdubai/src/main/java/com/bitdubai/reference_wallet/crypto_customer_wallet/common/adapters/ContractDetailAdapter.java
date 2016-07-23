package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.ContractDetailViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail.ContractDetailActivityFragment;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/01/16.
 * Modified by Alejandro Bicelis on 22/02/2016
 */
public class ContractDetailAdapter extends RecyclerView.Adapter<ContractDetailViewHolder> {

    //Holder Types
    private static final int TYPE_CUSTOMER = 0;
    private static final int TYPE_BROKER = 1;

    private Context context;
    private List<ContractDetail> dataSet;
    private ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> walletSession;
    private CryptoCustomerWalletModuleManager walletManager;
    private ContractDetailActivityFragment fragment;

    public ContractDetailAdapter(Context context, List<ContractDetail> dataSet, ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> walletSession,
                                 CryptoCustomerWalletModuleManager walletManager, ContractDetailActivityFragment fragment) {
        this.context = context;
        this.dataSet = dataSet;
        this.walletSession = walletSession;
        this.walletManager = walletManager;
        this.fragment = fragment;
    }

    protected ContractDetailViewHolder createHolder(View itemView, int type) {
        return new ContractDetailViewHolder(itemView, fragment);
    }

    protected int getCardViewResource() {
        return R.layout.ccw_contract_detail_item;
    }

    @Override
    public ContractDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(), parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(ContractDetailViewHolder holder, int position) {
        int holderType = getItemViewType(position);

        switch (holderType) {
            case TYPE_BROKER:
                ContractDetail brokerViewHolder = dataSet.get(position);
                holder.setWalletModuleManager(this.walletManager);
                holder.setSession(this.walletSession);
                holder.setParentFragment(
                        (ContractDetailActivityFragment) this.walletSession.getData(
                                "ContractDetailFragment"));
                holder.setErrorManager(this.walletSession.getErrorManager());
                holder.bind(brokerViewHolder);
                break;
            case TYPE_CUSTOMER:
                ContractDetail customerHolder = dataSet.get(position);
                holder.setWalletModuleManager(this.walletManager);
                holder.setSession(this.walletSession);
                holder.setParentFragment(
                        (ContractDetailActivityFragment) this.walletSession.getData(
                                "ContractDetailFragment"));
                holder.setErrorManager(this.walletSession.getErrorManager());
                holder.bind(customerHolder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

