package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class PaymentRequestItemViewHolder extends FermatViewHolder {
    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;

    ViewInflater viewInflater;


    public PaymentRequestItemViewHolder(View itemView, ViewInflater viewInflater) {
        super(itemView);

        contactIcon = (ImageView) itemView.findViewById(viewInflater.getIdFromName("imageView_contact"));
        txt_contactName = (TextView) itemView.findViewById(viewInflater.getIdFromName("txt_contact_name"));
        txt_amount = (TextView) itemView.findViewById(viewInflater.getIdFromName("txt_amount"));
        txt_notes = (TextView) itemView.findViewById(viewInflater.getIdFromName("txt_notes"));
        txt_time = (TextView) itemView.findViewById(viewInflater.getIdFromName("txt_time"));

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
