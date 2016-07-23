package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by Yordin Alayn on 23.01.16.
 * Based in NoteViewHolder of negotiation_details by nelson
 */
public class NoteViewHolder extends FermatViewHolder {

    TextView noteTextView;

    public NoteViewHolder(View itemView) {
        super(itemView);

        noteTextView = (TextView) itemView.findViewById(R.id.note_text);
    }

    public void bind(String text) {
        noteTextView.setText(text);
    }
}
