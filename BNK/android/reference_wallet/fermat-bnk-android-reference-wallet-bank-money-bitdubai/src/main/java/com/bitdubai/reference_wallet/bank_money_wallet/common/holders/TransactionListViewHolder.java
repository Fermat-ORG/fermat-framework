package com.bitdubai.reference_wallet.bank_money_wallet.common.holders;

import android.text.format.DateUtils;
import android.view.View;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private FermatTextView memo;

    public TransactionListViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        transactionAmount = (FermatTextView) itemView.findViewById(R.id.transaction_amount);
        currency = (FermatTextView) itemView.findViewById(R.id.transaction_currency);
        transactionType = (FermatTextView) itemView.findViewById(R.id.transaction_type);
        transactionTimestamp = (FermatTextView) itemView.findViewById(R.id.transaction_timestamp);
        memo = (FermatTextView) itemView.findViewById(R.id.memo);
    }

    public void bind(BankMoneyTransactionRecord itemInfo){
        itemView.setBackgroundColor(335);
        transactionAmount.setText(String.valueOf(itemInfo.getAmount()));
        currency.setText(itemInfo.getCurrencyType().getCode());
        transactionType.setText(itemInfo.getTransactionType().getCode());
        //transactionTimestamp.setText(String.valueOf(convertTime(itemInfo.getTimestamp())));
        transactionTimestamp.setText(DateUtils.getRelativeTimeSpanString(itemInfo.getTimestamp()));
        memo.setText(itemInfo.getMemo());
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}
