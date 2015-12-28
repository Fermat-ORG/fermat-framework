package com.bitdubai.reference_wallet.bank_money_wallet.common.adapters;

import android.content.Context;
import android.view.View;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.common.holders.TransactionListViewHolder;

import java.util.List;

/**
 * Created by memo on 28/12/15.
 */
public class TransactionListAdapter extends FermatAdapter<BankMoneyTransactionRecord, TransactionListViewHolder> {
    public TransactionListAdapter(Context context, List<BankMoneyTransactionRecord> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected TransactionListViewHolder createHolder(View itemView, int type) {
        return new TransactionListViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.bw_account_list_item;
    }

    @Override
    protected void bindHolder(TransactionListViewHolder holder, BankMoneyTransactionRecord data, int position) {
        holder.bind(data);
    }
}
