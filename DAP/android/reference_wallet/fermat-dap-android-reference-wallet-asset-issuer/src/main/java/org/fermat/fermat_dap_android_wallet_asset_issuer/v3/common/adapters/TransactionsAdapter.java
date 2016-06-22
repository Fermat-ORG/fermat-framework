package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Transaction;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.holders.TransactionsViewHolder;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class TransactionsAdapter extends FermatAdapter<Transaction, TransactionsViewHolder> {

    private AssetIssuerWalletSupAppModuleManager manager;

    public TransactionsAdapter(Context context, List<Transaction> transactions, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, transactions);
        this.manager = manager;
    }

    @Override
    protected TransactionsViewHolder createHolder(View itemView, int type) {
        return new TransactionsViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_transactions_item;
    }

    @Override
    protected void bindHolder(TransactionsViewHolder holder, Transaction data, int position) {
        holder.bind(data);
    }
}
