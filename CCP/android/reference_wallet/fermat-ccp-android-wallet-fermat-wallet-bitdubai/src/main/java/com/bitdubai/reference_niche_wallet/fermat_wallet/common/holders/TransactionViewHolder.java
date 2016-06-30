package com.bitdubai.reference_niche_wallet.fermat_wallet.common.holders;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by Matias Furszyfer on 28/10/15.
 */
public class TransactionViewHolder extends FermatViewHolder {

    private TextView txt_amount;
    private TextView txt_user;
    private TextView txt_memo;
    private TextView txt_date;
    private TextView txt_from_or_to_transaction;




    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public TransactionViewHolder(View itemView) {
        super(itemView);

        txt_amount = (TextView) itemView.findViewById(R.id.fermat_amount);
        txt_memo = (TextView) itemView.findViewById(R.id.transaction_memo);
        txt_date = (TextView) itemView.findViewById(R.id.date);
        txt_user = (TextView) itemView.findViewById(R.id.transacion_user);
        txt_from_or_to_transaction = (TextView) itemView.findViewById(R.id.fromOrToType);
    }

    public TextView getTxt_amount(){return txt_amount;}

    public TextView getTxt_memo(){return txt_memo;}

    public TextView getTxt_date(){return txt_date;}

    public TextView getTxt_user(){return txt_user;}

    public TextView getTxt_from_or_to_transaction(){return txt_from_or_to_transaction;}


}
