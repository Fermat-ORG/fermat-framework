package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

/**
 * Created by nelson on 10/01/16.
 */
public abstract class ClauseViewHolder extends FermatViewHolder {
    protected Button confirmButton;
    protected ImageView clauseNumberImageView;
    protected TextView titleTextView;

    protected Listener listener;
    protected ClauseInformation clause;
    protected CustomerBrokerNegotiationInformation negotiationInformation;
    protected int clausePosition;

    public ClauseViewHolder(View itemView) {
        super(itemView);

        confirmButton = (Button) itemView.findViewById(getConfirmButtonRes());
        clauseNumberImageView = (ImageView) itemView.findViewById(getClauseNumberImageViewRes());
        titleTextView = (TextView) itemView.findViewById(getTitleTextViewRes());
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void bindData(CustomerBrokerNegotiationInformation negotiationInformation, ClauseInformation clause, int clausePosition) {
        this.negotiationInformation = negotiationInformation;
        this.clause = clause;
        this.clausePosition = clausePosition;
    }

    public abstract void setViewResources(int titleRes, int positionImgRes, int... stringResources);

    protected abstract int getConfirmButtonRes();

    protected abstract int getClauseNumberImageViewRes();

    protected abstract int getTitleTextViewRes();

    public interface Listener {
        void onClauseCLicked(Button triggerView, ClauseInformation clause, int clausePosition);
    }
}
