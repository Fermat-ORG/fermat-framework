package com.bitdubai.reference_wallet.bank_money_wallet.common.holders;

import android.content.res.Resources;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;

import com.bitdubai.reference_wallet.bank_money_wallet.R;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by memo on 28/12/15.
 */
public class TransactionListViewHolder extends FermatViewHolder {

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
    private static final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");


    private ProgressBar progressBar;

    public TransactionListViewHolder(View itemView) {
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

        progressBar = (ProgressBar) itemView.findViewById(R.id.bw_transaction_progress_bar);
    }

    public void bind(BankMoneyTransactionRecord itemInfo){

        resetVisibility();
        long timestamp;

        if(itemInfo.getStatus()== BankTransactionStatus.PENDING){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(false);
            progressBar.setMax(15);
            int timeDiff = (int) (new Date().getTime()/1000 - itemInfo.getTimestamp());
            progressBar.setProgress(timeDiff);
            progressBar.getProgressDrawable().setColorFilter(
                    getTransactionTypeColorProgressBar(itemInfo.getTransactionType()),
                    android.graphics.PorterDuff.Mode.SRC_IN);
            timestamp = itemInfo.getTimestamp() * 1000;

            depositDate.setText(DateUtils.getRelativeTimeSpanString(timestamp).toString());
        }else{
            progressBar.setVisibility(View.GONE);
            timestamp = itemInfo.getTimestamp();

        }

        if(itemInfo.getTransactionType() == TransactionType.CREDIT) {

            depositItemsVisible();
            depositText.setText("DEPOSIT");
            depositAmount.setText(moneyFormat.format(itemInfo.getAmount()));
            depositDate.setText(DateUtils.getRelativeTimeSpanString(timestamp).toString());
            depositMemo.setText(itemInfo.getMemo());
        }
        if(itemInfo.getTransactionType() == TransactionType.UNHOLD) {

            depositItemsVisible();
            depositText.setText("UNHOLD");
            depositAmount.setText(moneyFormat.format(itemInfo.getAmount()));
            depositDate.setText(DateUtils.getRelativeTimeSpanString(timestamp).toString());
            depositMemo.setText(itemInfo.getMemo());
        }
        if(itemInfo.getTransactionType() == TransactionType.HOLD) {

            withdrawalItemsVisible();
            withdrawalText.setText("HOLD");
            withdrawalAmount.setText(moneyFormat.format(itemInfo.getAmount()));
            withdrawalDate.setText(DateUtils.getRelativeTimeSpanString(timestamp).toString());
            withdrawalMemo.setText(itemInfo.getMemo());
        }
        if(itemInfo.getTransactionType() == TransactionType.DEBIT) {

            withdrawalItemsVisible();
            withdrawalText.setText("WITHDRAWAL");
            withdrawalAmount.setText(moneyFormat.format(itemInfo.getAmount()));
            withdrawalDate.setText(DateUtils.getRelativeTimeSpanString(timestamp).toString());
            withdrawalMemo.setText(itemInfo.getMemo());
        }
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }

    private void resetVisibility() {
        depositImage.setVisibility(View.GONE);
        depositText.setVisibility(View.GONE);
        depositAmount.setVisibility(View.GONE);
        depositMemo.setVisibility(View.GONE);
        depositDate.setVisibility(View.GONE);
        withdrawalImage.setVisibility(View.GONE);
        withdrawalText.setVisibility(View.GONE);
        withdrawalAmount.setVisibility(View.GONE);
        withdrawalMemo.setVisibility(View.GONE);
        withdrawalDate.setVisibility(View.GONE);
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

    private int getTransactionTypeColorProgressBar(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return res.getColor(R.color.bnk_transaction_withdrawal_color);
        else if (transactionType == TransactionType.CREDIT)
            return res.getColor(R.color.bnk_transaction_deposit_color);
        else return -1;
    }
}
