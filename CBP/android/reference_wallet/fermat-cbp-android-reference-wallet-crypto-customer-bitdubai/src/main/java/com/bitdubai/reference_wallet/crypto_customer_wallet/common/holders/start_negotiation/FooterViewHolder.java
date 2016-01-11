package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.util.List;

/**
 * Created by nelson on 10/01/16.
 */
public class FooterViewHolder extends FermatViewHolder implements View.OnClickListener {
    OnFooterButtonsClickListener listener;

    public FooterViewHolder(View itemView) {
        super(itemView);

        CardView addNoteButton = (CardView) itemView.findViewById(R.id.add_a_note_card_view);
        addNoteButton.setVisibility(View.GONE);

        TextView sendButton = (TextView) itemView.findViewById(R.id.send_button);
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
