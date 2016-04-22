package com.bitdubai.reference_wallet.bank_money_wallet.common.holders;

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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by memo on 28/12/15.
 */
public class TransactionListViewHolder extends FermatViewHolder {

    private View itemView;
    /*private FermatTextView transaction;
    private FermatTextView transactionAmount;
    private FermatTextView currency;
    private FermatTextView transactionType;
    private FermatTextView transactionTimestamp;
    private FermatTextView memo;*/


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


    private ProgressBar progressBar;

    public TransactionListViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        /*transactionAmount = (FermatTextView) itemView.findViewById(R.id.transaction_amount);
        currency = (FermatTextView) itemView.findViewById(R.id.transaction_currency);
        transactionType = (FermatTextView) itemView.findViewById(R.id.transaction_type);
        transactionTimestamp = (FermatTextView) itemView.findViewById(R.id.transaction_timestamp);
        memo = (FermatTextView) itemView.findViewById(R.id.memo);*/
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

        if(itemInfo.getStatus()== BankTransactionStatus.PENDING){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }

        if(itemInfo.getTransactionType() == TransactionType.CREDIT) {

            depositItemsVisible();
            depositText.setText("DEPOSIT");
            depositAmount.setText(String.valueOf(itemInfo.getAmount()));
            depositDate.setText(DateUtils.getRelativeTimeSpanString(itemInfo.getTimestamp()).toString());
            depositMemo.setText(itemInfo.getMemo());
        }
        if(itemInfo.getTransactionType() == TransactionType.UNHOLD) {

            depositItemsVisible();
            depositText.setText("UNHOLD");
            depositAmount.setText(String.valueOf(itemInfo.getAmount()));
            depositDate.setText(DateUtils.getRelativeTimeSpanString(itemInfo.getTimestamp()).toString());
            depositMemo.setText(itemInfo.getMemo());
        }
        if(itemInfo.getTransactionType() == TransactionType.HOLD) {

            withdrawalItemsVisible();
            withdrawalText.setText("HOLD");
            withdrawalAmount.setText(String.valueOf(itemInfo.getAmount()));
            withdrawalDate.setText(DateUtils.getRelativeTimeSpanString(itemInfo.getTimestamp()).toString());
            withdrawalMemo.setText(itemInfo.getMemo());
        }
        if(itemInfo.getTransactionType() == TransactionType.DEBIT) {

            withdrawalItemsVisible();
            withdrawalText.setText("WITHDRAWAL");
            withdrawalAmount.setText(String.valueOf(itemInfo.getAmount()));
            withdrawalDate.setText(DateUtils.getRelativeTimeSpanString(itemInfo.getTimestamp()).toString());
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
}
