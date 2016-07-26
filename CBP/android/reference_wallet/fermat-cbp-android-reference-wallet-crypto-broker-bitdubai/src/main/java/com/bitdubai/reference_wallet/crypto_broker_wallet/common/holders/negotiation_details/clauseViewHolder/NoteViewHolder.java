package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by Yordin Alayn on 23.01.16.
 * Based in NoteViewHolder of negotiation_details by nelson
 */
public class NoteViewHolder extends FermatViewHolder {

    TextView noteTextView;

    public NoteViewHolder(View itemView, int holderType) {
        super(itemView, holderType);

        noteTextView = (TextView) itemView.findViewById(R.id.note_text);
    }

    public void bind(String text) {
        noteTextView.setText(text);
    }
}
