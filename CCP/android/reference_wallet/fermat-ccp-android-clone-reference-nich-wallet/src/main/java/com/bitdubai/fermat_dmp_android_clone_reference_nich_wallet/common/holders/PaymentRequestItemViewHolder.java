package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class PaymentRequestItemViewHolder extends FermatViewHolder {
    private ImageView contactIcon;
    private FermatTextView txt_contactName;
    private FermatTextView txt_amount;
    private FermatTextView txt_notes;
    private FermatTextView txt_time;

    ViewInflater viewInflater;


    public PaymentRequestItemViewHolder(View itemView, ViewInflater viewInflater) {
        super(itemView);

        contactIcon = (ImageView) itemView.findViewById(viewInflater.getIdFromName("imageView_contact"));
        txt_contactName = (FermatTextView) itemView.findViewById(viewInflater.getIdFromName("txt_contact_name"));
        txt_amount = (FermatTextView) itemView.findViewById(viewInflater.getIdFromName("txt_amount"));
        txt_notes = (FermatTextView) itemView.findViewById(viewInflater.getIdFromName("txt_notes"));
        txt_time = (FermatTextView) itemView.findViewById(viewInflater.getIdFromName("txt_time"));

    }

    public ImageView getContactIcon() {
        return contactIcon;
    }

    public FermatTextView getTxt_contactName() {
        return txt_contactName;
    }

    public FermatTextView getTxt_amount() {
        return txt_amount;
    }

    public FermatTextView getTxt_notes() {
        return txt_notes;
    }


    public FermatTextView getTxt_time() {
        return txt_time;
    }
}
