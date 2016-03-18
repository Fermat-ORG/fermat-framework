package com.bitdubai.reference_wallet.cash_money_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.reference_wallet.cash_money_wallet.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Alejandro Bicelis on 12/11/2015.
 */
public class TransactionsViewHolder extends FermatViewHolder {
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private Resources res;

    private ProgressBar spinner;

    public FermatTextView transactionType;
    public FermatTextView amount;
    public FermatTextView date;
    public FermatTextView memo;

    public TransactionsViewHolder(View itemView) {
        super(itemView);

        res = itemView.getResources();
        spinner = (ProgressBar) itemView.findViewById(R.id.cash_transaction_list_spinner);

        transactionType = (FermatTextView) itemView.findViewById(R.id.csh_list_item_transaction_type);
        amount = (FermatTextView) itemView.findViewById(R.id.csh_list_item_amount);
        date = (FermatTextView) itemView.findViewById(R.id.csh_list_item_date);
        memo = (FermatTextView) itemView.findViewById(R.id.csh_list_item_memo);
    }

    public void bind(CashMoneyWalletTransaction itemInfo) {
        if(itemInfo.isPending())
            spinner.setVisibility(View.VISIBLE);
        else
            spinner.setVisibility(View.INVISIBLE);

        date.setText(getPrettyTime(itemInfo.getTimestamp()));
        memo.setText(itemInfo.getMemo());
        amount.setText(itemInfo.getAmount().toPlainString());
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
        else
            return res.getString(R.string.csh_deposit_transaction_text_caps);
    }

    private int getTransactionTypeColor(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return res.getColor(R.color.csh_transaction_withdrawal_color);
        else
            return res.getColor(R.color.csh_transaction_deposit_color);
    }

}
