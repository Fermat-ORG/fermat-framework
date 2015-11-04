package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

import org.w3c.dom.Text;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class PaymentHistoryItemViewHolder extends FermatViewHolder {
    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;

    private TextView txt_state;




    public PaymentHistoryItemViewHolder(View itemView) {
        super(itemView);

        contactIcon = (ImageView) itemView.findViewById(R.id.profile_Image);
        txt_contactName = (TextView) itemView.findViewById(R.id.txt_contactName);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);
        txt_state = (TextView) itemView.findViewById(R.id.txt_state);



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

    public TextView getTxt_state() {
        return txt_state;
    }
}
