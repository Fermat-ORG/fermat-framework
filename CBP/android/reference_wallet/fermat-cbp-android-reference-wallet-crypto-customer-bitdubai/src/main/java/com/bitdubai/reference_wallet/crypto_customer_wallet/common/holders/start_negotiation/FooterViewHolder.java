package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;


/**
 * Created by nelson on 10/01/16.
 */
public class FooterViewHolder extends FermatViewHolder implements View.OnClickListener {
    OnFooterButtonsClickListener listener;

    CardView addNoteButton;
    TextView sendButton;
    CardView addWarningWalletUser;

    public FooterViewHolder(View itemView) {
        super(itemView);

        addNoteButton = (CardView) itemView.findViewById(R.id.add_a_note_card_view);
        addNoteButton.setVisibility(View.GONE);

        sendButton = (TextView) itemView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
        sendButton.setVisibility(View.VISIBLE);

        addWarningWalletUser = (CardView) itemView.findViewById(R.id.warning_wallet_user);
        addWarningWalletUser.setVisibility(View.GONE);

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

    public void HideButtonsWalletUser() {
        addNoteButton.setVisibility(View.GONE);
        sendButton.setVisibility(View.GONE);
        addWarningWalletUser.setVisibility(View.VISIBLE);
    }
}
