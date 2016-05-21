package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by root on 18/05/16.
 */
public class TransactionListItemViewHolder extends FermatViewHolder {

    /**
     * UI Components
     * **/
    private TextView transaction_amount;
    private TextView transaction_date;
    private TextView transaction_user;
    private TextView transaction_note;

    public TransactionListItemViewHolder(View itemView) {
        super(itemView);

        transaction_amount = (TextView) itemView.findViewById(R.id.transaction_amount);
        transaction_date = (TextView) itemView.findViewById(R.id.transaction_date);
        transaction_user = (TextView) itemView.findViewById(R.id.transaction_user);
        transaction_note = (TextView) itemView.findViewById(R.id.transaction_note);
    }

    public TextView getTransaction_amount(){return transaction_amount;}

    public TextView getTransaction_date(){return transaction_date;}

    public TextView getTransaction_user(){return transaction_user;}

    public TextView getTransaction_note(){return transaction_note;}
}
