package com.bitdubai.reference_wallet.cash_money_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;

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
    private View itemView;

    public ImageView depositImage;
    public FermatTextView depositText;
    public FermatTextView depositAmount;
    public FermatTextView depositDate;
    public FermatTextView depositMemo;

    public ImageView withdrawalImage;
    public FermatTextView withdrawalText;
    public FermatTextView withdrawalAmount;
    public FermatTextView withdrawalDate;
    public FermatTextView withdrawalMemo;


    public TransactionsViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();

        depositImage = (ImageView) itemView.findViewById(R.id.imageView_deposit);
        depositText = (FermatTextView) itemView.findViewById(R.id.textView_deposit);
        depositAmount = (FermatTextView) itemView.findViewById(R.id.textView_deposit_amount);
        depositDate = (FermatTextView) itemView.findViewById(R.id.textView_deposit_date);
        depositMemo = (FermatTextView) itemView.findViewById(R.id.textView_deposit_memo);

        withdrawalImage = (ImageView) itemView.findViewById(R.id.imageView_withdrawal);
        withdrawalText = (FermatTextView) itemView.findViewById(R.id.textView_withdrawal);
        withdrawalAmount = (FermatTextView) itemView.findViewById(R.id.textView_withdrawal_amount);
        withdrawalDate = (FermatTextView) itemView.findViewById(R.id.textView_withdrawal_date);
        withdrawalMemo = (FermatTextView) itemView.findViewById(R.id.textView_withdrawal_memo);
    }

    public void bind(CashMoneyWalletTransaction itemInfo) {
        resetVisibility();

        if(itemInfo.getTransactionType() == TransactionType.CREDIT) {

            depositItemsVisible();
            depositAmount.setText(getTransactionAmountText(itemInfo.getTransactionType(), itemInfo.getAmount()));
            depositDate.setText(getPrettyTime(itemInfo.getTimestamp()));
            depositMemo.setText(itemInfo.getMemo());
        }
        else {

            withdrawalItemsVisible();
            withdrawalAmount.setText(getTransactionAmountText(itemInfo.getTransactionType(), itemInfo.getAmount()));
            withdrawalDate.setText(getPrettyTime(itemInfo.getTimestamp()));
            withdrawalMemo.setText(itemInfo.getMemo());
        }

    }

    /* HELPER FUNCTIONS */
    private String getPrettyTime(long timestamp)
    {
        return DateUtils.getRelativeTimeSpanString(timestamp * 1000).toString();
        //return DateFormat.format("dd MMM yyyy h:mm:ss aa", (timestamp * 1000)).toString();
    }
    private Bitmap getImgBitmap(TransactionType transactionType) {
        if(transactionType == TransactionType.DEBIT)
            return BitmapFactory.decodeResource(res, R.drawable.csh_withdrawal);
        else
            return BitmapFactory.decodeResource(res, R.drawable.csh_deposit);
    }

    private String getTransactionTypeText(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return res.getString(R.string.withdrawal_transaction_text);
        else
            return res.getString(R.string.deposit_transaction_text);
    }
    private String getTransactionAmountText(TransactionType transactionType, BigDecimal amount)
    {
        //return (transactionType == TransactionType.CREDIT ? "+" : "-") + decimalFormat.format(amount);
        //return (transactionType == TransactionType.CREDIT ? "+" : "-") + amount;
        return amount.toPlainString();
    }

    private void resetVisibility() {
        depositImage.setVisibility(View.INVISIBLE);
        depositText.setVisibility(View.INVISIBLE);
        depositAmount.setVisibility(View.INVISIBLE);
        depositMemo.setVisibility(View.INVISIBLE);
        depositDate.setVisibility(View.INVISIBLE);
        withdrawalImage.setVisibility(View.INVISIBLE);
        withdrawalText.setVisibility(View.INVISIBLE);
        withdrawalAmount.setVisibility(View.INVISIBLE);
        withdrawalMemo.setVisibility(View.INVISIBLE);
        withdrawalDate.setVisibility(View.INVISIBLE);
    }

    private void depositItemsVisible() {
        depositImage.setVisibility(View.VISIBLE);
        depositText.setVisibility(View.VISIBLE);
        depositAmount.setVisibility(View.VISIBLE);
        depositMemo.setVisibility(View.VISIBLE);
        depositDate.setVisibility(View.VISIBLE);
    }

    private void withdrawalItemsVisible() {
        withdrawalImage.setVisibility(View.VISIBLE);
        withdrawalText.setVisibility(View.VISIBLE);
        withdrawalAmount.setVisibility(View.VISIBLE);
        withdrawalMemo.setVisibility(View.VISIBLE);
        withdrawalDate.setVisibility(View.VISIBLE);
    }
}
