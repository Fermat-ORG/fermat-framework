package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 *Created by Nelson Ramirez
 * @since 17-02-2016.
 */
public class FooterViewHolder extends FermatViewHolder implements View.OnClickListener {
    OnFooterButtonsClickListener listener;

    public FooterViewHolder(View itemView, int holderType) {
        super(itemView, holderType);

        CardView addNoteButton = (CardView) itemView.findViewById(R.id.add_a_note_card_view);
        addNoteButton.setOnClickListener(this);

        TextView sendButton = (FermatTextView) itemView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_a_note_card_view) {
            listener.onAddNoteButtonClicked();

        } else if (view.getId() == R.id.send_button) {
            listener.onSendButtonClicked();
        }
    }

    public void setListener(OnFooterButtonsClickListener listener) {
        this.listener = listener;
    }

    public interface OnFooterButtonsClickListener {
        void onAddNoteButtonClicked();

        void onSendButtonClicked();
    }
}