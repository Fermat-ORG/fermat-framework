package com.bitdubai.reference_niche_wallet.fermat_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class TransactionHistoryItemViewHolder extends FermatViewHolder {
    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;




    public TransactionHistoryItemViewHolder(View itemView) {
        super(itemView);

        contactIcon = (ImageView) itemView.findViewById(R.id.profile_Image);
        txt_contactName = (TextView) itemView.findViewById(R.id.txt_contactName);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);



    }

    public ImageView getContactIcon() {
        return contactIcon;
    }

    public TextView getTxt_contactName() {
        return txt_contactName;
    }

    public TextView getTxt_amount() {
        return txt_amount;
    }

    public TextView getTxt_notes() {
        return txt_notes;
    }


    public TextView getTxt_time() {
        return txt_time;
    }


}
