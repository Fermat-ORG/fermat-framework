package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by root on 12/04/16.
 */
public class ChunckValuesDetailItemViewHolder extends FermatViewHolder {
    private TextView txt_date;
    private TextView txt_amount_balance;
    private TextView txt_exchange_rate;


    public ChunckValuesDetailItemViewHolder(View itemView) {
        super(itemView);

        txt_date = (TextView) itemView.findViewById(R.id.txt_date);
        txt_amount_balance = (TextView) itemView.findViewById(R.id.txt_amount_balance);
        txt_exchange_rate = (TextView) itemView.findViewById(R.id.txt_exchange_rate);
    }

    public TextView getDate(){ return txt_date;}

    public TextView getAmountBalance(){return txt_amount_balance;}

    public TextView getExchangeRate(){ return txt_exchange_rate;}
}
