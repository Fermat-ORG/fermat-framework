package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.holders.AssetDetailRecievedHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Transaction;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class AssetDetailRecievedAdapter extends FermatAdapter<Transaction, AssetDetailRecievedHolder> {

    private AssetUserWalletSubAppModuleManager manager;

    public AssetDetailRecievedAdapter(Context context, List<Transaction> transactions, AssetUserWalletSubAppModuleManager manager) {
        super(context, transactions);
        this.manager = manager;
    }

    @Override
    protected AssetDetailRecievedHolder createHolder(View itemView, int type) {
        return new AssetDetailRecievedHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_user_transaction_recieved_item;
    }

    @Override
    protected void bindHolder(AssetDetailRecievedHolder holder, Transaction data, int position) {
        holder.bind(data);
    }
}
