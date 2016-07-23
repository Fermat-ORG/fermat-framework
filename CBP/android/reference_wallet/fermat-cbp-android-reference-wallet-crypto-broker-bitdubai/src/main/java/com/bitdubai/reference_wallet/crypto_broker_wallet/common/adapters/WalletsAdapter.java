package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CBPInstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.WalletViewHolder;

import java.util.List;

/**
 * Created by nelson on 28/12/15.
 */
//public class WalletsAdapter extends SingleDeletableItemAdapter<InstalledWallet, WalletViewHolder> {
public class WalletsAdapter extends SingleDeletableItemAdapter<CBPInstalledWallet, WalletViewHolder> {

    //    public WalletsAdapter(Context context, List<InstalledWallet> dataSet) {
    public WalletsAdapter(Context context, List<CBPInstalledWallet> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected WalletViewHolder createHolder(View itemView, int type) {
        return new WalletViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_wizard_recycler_view_item;
    }
}
