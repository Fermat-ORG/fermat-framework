package com.bitdubai.reference_wallet.cash_money_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.reference_wallet.cash_money_wallet.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Alejandro Bicelis on 12/11/2015.
 */
public class TransactionsViewHolder extends FermatViewHolder {
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private Resources res;
    private View itemView;

    public ImageView transactionTypeImage;
    public FermatTextView transactionType;
    public FermatTextView transactionAmount;
    public FermatTextView transactionDate;
    public FermatTextView transactionMemo;


    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public TransactionsViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();

        transactionTypeImage = (ImageView) itemView.findViewById(R.id.imageView_transaction_type_image);
        transactionType = (FermatTextView) itemView.findViewById(R.id.textView_transaction_type);
        transactionAmount = (FermatTextView) itemView.findViewById(R.id.textView_transaction_amount);
        transactionDate = (FermatTextView) itemView.findViewById(R.id.textView_transaction_date);
        transactionMemo = (FermatTextView) itemView.findViewById(R.id.textView_transaction_memo);
    }

    public void bind(CashMoneyWalletTransaction itemInfo) {
        //itemView.setBackgroundColor(getTransactionColor(itemInfo.getTransactionType()));

        transactionTypeImage.setImageBitmap(getImgBitmap(itemInfo.getTransactionType()));

        transactionType.setText(getTransactionTypeText(itemInfo.getTransactionType()));

        transactionAmount.setText(getTransactionAmountText(itemInfo.getTransactionType(), itemInfo.getAmount()));
        transactionAmount.setTextColor(getTransactionColor(itemInfo.getTransactionType()));

        transactionDate.setText(DateFormat.format("dd MMM yyyy h:mm:ss aa", (itemInfo.getTimestamp()*1000)));

        transactionMemo.setText(itemInfo.getMemo());
    }



    /* HELPER FUNCTIONS */
    private Bitmap getImgBitmap(TransactionType transactionType) {
        if(transactionType == TransactionType.DEBIT)
            return BitmapFactory.decodeResource(res, R.drawable.ic_action_upload_grey);
        else
            return BitmapFactory.decodeResource(res, R.drawable.ic_action_download_grey);
    }

    private int getTransactionColor(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return res.getColor(R.color.csh_transaction_withdrawal_color);
        else
            return res.getColor(R.color.csh_transaction_deposit_color);
    }

    private String getTransactionTypeText(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return res.getString(R.string.withdrawal_transaction_text);
        else
            return res.getString(R.string.deposit_transaction_text);
    }

    private String getTransactionAmountText(TransactionType transactionType, double amount)
    {
        return (transactionType == TransactionType.CREDIT ? "+" : "-") + decimalFormat.format(amount);
    }

}
