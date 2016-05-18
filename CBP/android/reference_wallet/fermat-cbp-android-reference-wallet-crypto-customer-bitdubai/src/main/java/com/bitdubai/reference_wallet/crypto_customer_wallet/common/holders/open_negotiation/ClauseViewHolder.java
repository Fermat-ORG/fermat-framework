package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;


/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in ClauseViewHolder of Star_negotiation by nelson
 */
public abstract class ClauseViewHolder extends FermatViewHolder {

    private final Resources res;

    protected NegotiationStepStatus actualStatus;
    protected boolean valuesHasChanged;
    protected Button confirmButton;
    protected ImageView clauseNumberImageView;
    protected TextView titleTextView;

    protected Listener listener;
    //    protected ListenerConfirm listenerConfirm;
    protected ClauseInformation clause;
    protected CustomerBrokerNegotiationInformation negotiationInformation;
    protected int clausePosition;

    public ClauseViewHolder(View itemView) {
        super(itemView);

        res = itemView.getResources();
        actualStatus = NegotiationStepStatus.CONFIRM;
        valuesHasChanged = false;

        confirmButton = (Button) itemView.findViewById(getConfirmButtonRes());
        clauseNumberImageView = (ImageView) itemView.findViewById(getClauseNumberImageViewRes());
        titleTextView = (TextView) itemView.findViewById(getTitleTextViewRes());

        configClauseViews(itemView);
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
        void onClauseClicked(Button triggerView, ClauseInformation clause, int clausePosition);

        void onConfirmCLicked(ClauseInformation clause);
    }

    private void configClauseViews(View itemView) {

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onConfirmCLicked(clause);
            }
        });

    }

    public void setStatus(NegotiationStepStatus stepStatus) {
        CardView containerCardView = (CardView) this.itemView;

        switch (stepStatus) {
            case ACCEPTED:
                containerCardView.setCardBackgroundColor(getColor(R.color.card_background_status_accepted));
                containerCardView.setClickable(false);
                confirmButton.setText(R.string.status_accepted);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                break;

            case CHANGED:
                containerCardView.setCardBackgroundColor(getColor(R.color.card_background_status_changed));
                containerCardView.setClickable(false);
                confirmButton.setText(R.string.status_changed);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                break;

            case CONFIRM:
                containerCardView.setCardBackgroundColor(getColor(R.color.card_background_status_confirm));
                confirmButton.setText(R.string.status_confirm);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                break;
        }
    }

    @SuppressWarnings("deprecation")
    protected int getColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return res.getColor(colorResId, null);
        else
            return res.getColor(colorResId);
    }
}
