package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterImproved;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.ContractDetailViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contract_detail.ContractDetailActivityFragment;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/02/16.
 */
public class ContractDetailAdapter extends FermatAdapterImproved<ContractDetail,ContractDetailViewHolder> {

    //Holder Types
    private static final int NO_TYPE = Integer.MIN_VALUE;
    private static final int TYPE_CUSTOMER = 0;
    private static final int TYPE_BROKER = 1;

    private Context context;
    private List<ContractDetail> dataSet;
    private FermatSession session;
    private CryptoBrokerWalletManager walletManager;

   /* public ContractDetailAdapter(
            Context context,
            List<ContractDetail> dataSet,
            FermatSession session,
            CryptoBrokerWalletManager walletManager) {
        this.context=context;
        this.dataSet=dataSet;
        this.session=session;
        this.walletManager=walletManager;
    }*/

    @Override
    protected int getCardViewResource(int type) {
        return R.layout.cbw_contract_detail_item;
    }

    public ContractDetailAdapter(Context context, List<ContractDetail> dataSet, FermatSession session, CryptoBrokerWalletManager walletManager) {
        super(context, dataSet);
        this.context=context;
        this.dataSet=dataSet;
        this.session=session;
        this.walletManager=walletManager;
    }

    protected ContractDetailViewHolder createHolder(View itemView, int type) {
        return new ContractDetailViewHolder(itemView,type);
    }

    /*protected int getCardViewResource() {
        return R.layout.cbw_contract_detail_item;
    }*/


    protected void bindHolder(ContractDetailViewHolder holder, ContractDetail data, int position) {
        holder.bind(data);
    }



    /*@Override
    public ContractDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(), parent, false), viewType);
    }*/

    /*@Override
    public void onBindViewHolder(ContractDetailViewHolder holder, int position) {
        int holderType = getItemViewType(position);

        switch (holderType){
            case TYPE_BROKER:
                ContractDetail brokerViewHolder= dataSet.get(position);
                holder.setWalletModuleManager(this.walletManager);
                holder.setParentFragment(
                        (ContractDetailActivityFragment) this.session.getData(
                                "ContractDetailFragment"));
                holder.setErrorManager(this.session.getErrorManager());
                holder.bind(brokerViewHolder);
                break;
            case TYPE_CUSTOMER:
                ContractDetail customerHolder= dataSet.get(position);
                holder.setWalletModuleManager(this.walletManager);
                holder.setParentFragment(
                        (ContractDetailActivityFragment) this.session.getData(
                                "ContractDetailFragment"));
                holder.setErrorManager(this.session.getErrorManager());
                holder.bind(customerHolder);
                break;
        }
    }*/

    /*@Override
    public int getItemCount() {
        return dataSet.size();
    }*/
}

