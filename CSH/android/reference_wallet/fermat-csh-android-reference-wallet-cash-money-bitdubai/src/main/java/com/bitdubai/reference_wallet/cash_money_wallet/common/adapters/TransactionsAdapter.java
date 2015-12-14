package com.bitdubai.reference_wallet.cash_money_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.reference_wallet.cash_money_wallet.common.holders.TransactionsViewHolder;
import com.bitdubai.reference_wallet.cash_money_wallet.R;


import java.util.List;

/**
 * Created by Alejandro Bicelis on 12/11/2015.
 */
public class TransactionsAdapter extends FermatAdapter<CashMoneyWalletTransaction, TransactionsViewHolder> {

    public TransactionsAdapter(Context context, List<CashMoneyWalletTransaction> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected TransactionsViewHolder createHolder(View itemView, int type) {
        return new TransactionsViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.transaction_list_item;
    }

    @Override
    protected void bindHolder(TransactionsViewHolder holder, CashMoneyWalletTransaction data, int position) {
        holder.bind(data);
    }
}
