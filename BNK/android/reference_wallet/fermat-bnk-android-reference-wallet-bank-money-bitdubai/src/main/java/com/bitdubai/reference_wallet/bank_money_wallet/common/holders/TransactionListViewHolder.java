package com.bitdubai.reference_wallet.bank_money_wallet.common.holders;

import android.view.View;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by memo on 28/12/15.
 */
public class TransactionListViewHolder extends FermatViewHolder {

    private View itemView;
    private FermatTextView transaction;
    private FermatTextView transactionAmount;
    private FermatTextView currency;
    private FermatTextView transactionType;
    private FermatTextView transactionTimestamp;

    public TransactionListViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        transaction = (FermatTextView) itemView.findViewById(R.id.transaction);
        transactionAmount = (FermatTextView) itemView.findViewById(R.id.transaction_amount);
        currency = (FermatTextView) itemView.findViewById(R.id.transaction_currency);
        transactionType = (FermatTextView) itemView.findViewById(R.id.transaction_type);
        transactionTimestamp = (FermatTextView) itemView.findViewById(R.id.transaction_timestamp);
    }

    public void bind(BankMoneyTransactionRecord itemInfo){
        itemView.setBackgroundColor(335);
        transaction.setText(itemInfo.getBankTransactionId().toString());
        transactionAmount.setText(String.valueOf(itemInfo.getAmount()));
        currency.setText(itemInfo.getCurrencyType().getCode());
        transactionType.setText(itemInfo.getCurrencyType().getCode());
        transactionTimestamp.setText(String.valueOf(itemInfo.getTimestamp()));
    }
}
