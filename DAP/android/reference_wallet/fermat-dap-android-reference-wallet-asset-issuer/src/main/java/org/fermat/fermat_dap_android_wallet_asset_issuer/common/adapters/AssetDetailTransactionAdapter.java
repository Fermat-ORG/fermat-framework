package org.fermat.fermat_dap_android_wallet_asset_issuer.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.holders.AssetDetailTransactionHolder;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Transaction;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class AssetDetailTransactionAdapter extends FermatAdapter<Transaction, AssetDetailTransactionHolder> {

    private AssetIssuerWalletSupAppModuleManager manager;

    public AssetDetailTransactionAdapter(Context context, List<Transaction> transactions, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, transactions);
        this.manager = manager;
    }

    @Override
    protected AssetDetailTransactionHolder createHolder(View itemView, int type) {
        return new AssetDetailTransactionHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_transaction_item;
    }

    @Override
    protected void bindHolder(AssetDetailTransactionHolder holder, Transaction data, int position) {
        holder.bind(data);
    }
}
