package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nelsonalfo.testapplication.R;

/**
 * Created by nelson on 11/12/15.
 */
public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView noteTextView;

    public NoteViewHolder(View itemView) {
        super(itemView);

        noteTextView = (TextView) itemView.findViewById(R.id.note_text);
    }

    public void bind(String text) {
        noteTextView.setText(text);
    }
}
