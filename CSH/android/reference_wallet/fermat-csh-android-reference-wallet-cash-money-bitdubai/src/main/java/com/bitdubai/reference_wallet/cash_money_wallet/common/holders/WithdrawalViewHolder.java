package com.bitdubai.reference_wallet.cash_money_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
public class WithdrawalViewHolder extends FermatViewHolder {
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private Resources res;
    private View itemView;

    public RelativeLayout withdrawalLayout;
    public ImageView withdrawalImage;
    public FermatTextView withdrawalText;
    public FermatTextView withdrawalAmount;
    public FermatTextView withdrawalDate;
    public FermatTextView withdrawalMemo;


    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public WithdrawalViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();

        withdrawalLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_withdrawal);
        withdrawalImage = (ImageView) itemView.findViewById(R.id.imageView_withdrawal);
        withdrawalText = (FermatTextView) itemView.findViewById(R.id.textView_withdrawal);
        withdrawalAmount = (FermatTextView) itemView.findViewById(R.id.textView_withdrawal_amount);
        withdrawalDate = (FermatTextView) itemView.findViewById(R.id.textView_withdrawal_date);
        withdrawalMemo = (FermatTextView) itemView.findViewById(R.id.textView_withdrawal_memo);
    }

    public void bind(CashMoneyWalletTransaction itemInfo) {
        //itemView.setBackgroundColor(getTransactionColor(itemInfo.getTransactionType()));

        //transactionTypeImage.setImageBitmap(getImgBitmap(itemInfo.getTransactionType()));

        withdrawalLayout.setVisibility(View.VISIBLE);
        withdrawalImage.setImageBitmap(getImgBitmap(itemInfo.getTransactionType()));
        withdrawalText.setText(getTransactionTypeText(itemInfo.getTransactionType()));
        withdrawalAmount.setText(getTransactionAmountText(itemInfo.getTransactionType(), itemInfo.getAmount()));
        withdrawalDate.setText(DateFormat.format("dd MMM yyyy h:mm:ss aa", (itemInfo.getTimestamp() * 1000)));
        withdrawalMemo.setText(itemInfo.getMemo());

    }



    /* HELPER FUNCTIONS */
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
        return (transactionType == TransactionType.CREDIT ? "+" : "-") + amount;
    }

}
