package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by Nelson Ramirez
 *
 * @since 17-02-2016.
 */

public class FooterViewHolder extends FermatViewHolder implements View.OnClickListener {
    OnFooterButtonsClickListener listener;
    CardView addNoteButton;
    TextView sendButton;
    CardView addWarningWalletUser;

    public FooterViewHolder(View itemView, int holderType) {
        super(itemView, holderType);

        addNoteButton = (CardView) itemView.findViewById(R.id.add_a_note_card_view);
        addNoteButton.setOnClickListener(this);

        sendButton = (FermatTextView) itemView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);

        addWarningWalletUser = (CardView) itemView.findViewById(R.id.warning_wallet_user);
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

    public void HideButtonsWalletUser() {
        addNoteButton.setVisibility(View.GONE);
        sendButton.setVisibility(View.GONE);
        addWarningWalletUser.setVisibility(View.VISIBLE);
    }
}