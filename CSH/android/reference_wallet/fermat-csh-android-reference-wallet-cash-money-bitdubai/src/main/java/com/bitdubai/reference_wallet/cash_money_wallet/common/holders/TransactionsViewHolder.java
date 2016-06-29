package com.bitdubai.reference_wallet.cash_money_wallet.common.holders;

import android.content.res.Resources;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.reference_wallet.cash_money_wallet.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Created by Alejandro Bicelis on 12/11/2015.
 */
public class TransactionsViewHolder extends FermatViewHolder {
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private Resources res;

    private ProgressBar progressBar;

    public FermatTextView transactionType;
    public FermatTextView amount;
    public FermatTextView date;
    public FermatTextView memo;
    private static final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");


    public TransactionsViewHolder(View itemView) {
        super(itemView);

        res = itemView.getResources();
        progressBar = (ProgressBar) itemView.findViewById(R.id.cash_transaction_list_spinner);

        transactionType = (FermatTextView) itemView.findViewById(R.id.csh_list_item_transaction_type);
        amount = (FermatTextView) itemView.findViewById(R.id.csh_list_item_amount);
        date = (FermatTextView) itemView.findViewById(R.id.csh_list_item_date);
        memo = (FermatTextView) itemView.findViewById(R.id.csh_list_item_memo);
    }

    public void bind(CashMoneyWalletTransaction itemInfo) {
        if(itemInfo.isPending()) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(false);
            progressBar.setMax(15);
            int timeDiff = (int) (new Date().getTime()/1000 - itemInfo.getTimestamp());
            progressBar.setProgress(timeDiff);
            progressBar.getProgressDrawable().setColorFilter(
                    getTransactionTypeColorProgressBar(itemInfo.getTransactionType()),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else
            progressBar.setVisibility(View.INVISIBLE);

        date.setText(getPrettyTime(itemInfo.getTimestamp()));
        memo.setText(itemInfo.getMemo());
        amount.setText(moneyFormat.format(itemInfo.getAmount()));
        transactionType.setText(getTransactionTypeText(itemInfo.getTransactionType()));
        transactionType.setTextColor(getTransactionTypeColor(itemInfo.getTransactionType()));
    }


    /* HELPER FUNCTIONS */
    private String getPrettyTime(long timestamp)
    {
        return DateUtils.getRelativeTimeSpanString(timestamp * 1000).toString();
        //return DateFormat.format("dd MMM yyyy h:mm:ss aa", (timestamp * 1000)).toString();
    }

    private String getTransactionTypeText(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return res.getString(R.string.csh_withdrawal_transaction_text_caps);
        else if (transactionType == TransactionType.CREDIT)
            return res.getString(R.string.csh_deposit_transaction_text_caps);
        else if (transactionType == TransactionType.HOLD)
            return res.getString(R.string.csh_hold_transaction_text_caps);
        else if (transactionType == TransactionType.UNHOLD)
            return res.getString(R.string.csh_unhold_transaction_text_caps);
        else return "ERROR";
    }

    private int getTransactionTypeColor(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return res.getColor(R.color.csh_transaction_withdrawal_color);
        else if (transactionType == TransactionType.CREDIT)
            return res.getColor(R.color.csh_transaction_deposit_color);
        else if (transactionType == TransactionType.HOLD)
            return res.getColor(R.color.csh_transaction_hold_color);
        else if (transactionType == TransactionType.UNHOLD)
            return res.getColor(R.color.csh_transaction_unhold_color);
        else return -1;
    }

    private int getTransactionTypeColorProgressBar(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return res.getColor(R.color.csh_transaction_withdrawal_color_progress_bar);
        else if (transactionType == TransactionType.CREDIT)
            return res.getColor(R.color.csh_transaction_deposit_color_progress_bar);
        else if (transactionType == TransactionType.HOLD)
            return res.getColor(R.color.csh_transaction_hold_color_progress_bar);
        else if (transactionType == TransactionType.UNHOLD)
            return res.getColor(R.color.csh_transaction_unhold_color_progress_bar);
        else return -1;
    }

}
