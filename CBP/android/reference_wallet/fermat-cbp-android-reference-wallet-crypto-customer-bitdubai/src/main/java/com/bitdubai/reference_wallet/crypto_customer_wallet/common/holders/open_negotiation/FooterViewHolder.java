package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in FooterViewHolder of Star_negotiation by nelson
 */
public class FooterViewHolder extends FermatViewHolder implements View.OnClickListener {
    OnFooterButtonsClickListener listener;
    CardView addNoteButton;
    TextView sendButton;

    public FooterViewHolder(View itemView) {

        super(itemView);

        addNoteButton = (CardView) itemView.findViewById(R.id.add_a_note_card_view);
        addNoteButton.setVisibility(View.VISIBLE);
        addNoteButton.setOnClickListener(this);

        sendButton = (TextView) itemView.findViewById(R.id.send_button);
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

    public void HideButtons() {
        addNoteButton.setVisibility(View.GONE);
        sendButton.setVisibility(View.GONE);
    }

    public void setListener(OnFooterButtonsClickListener listener) {
        this.listener = listener;
    }

    public interface OnFooterButtonsClickListener {
        void onAddNoteButtonClicked();

        void onSendButtonClicked();
    }
}