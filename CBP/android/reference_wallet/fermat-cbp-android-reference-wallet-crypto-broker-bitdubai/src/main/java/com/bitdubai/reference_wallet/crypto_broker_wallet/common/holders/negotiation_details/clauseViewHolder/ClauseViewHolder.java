package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;

/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in ClauseViewHolder of Star_negotiation by nelson
 */
public abstract class ClauseViewHolder extends FermatViewHolder {

    private Resources res;
    private CardView containerCardView;

    protected FermatButton confirmButton;
    protected ImageView clauseNumberImageView;
    protected FermatTextView titleTextView;

    protected Listener listener;
    protected ClauseInformation clause;
    protected CustomerBrokerNegotiationInformation negotiationInformation;
    protected int clausePosition;

    public ClauseViewHolder(View itemView, int holderType) {
        super(itemView, holderType);
        res = itemView.getResources();

        containerCardView = (CardView) this.itemView;
        clauseNumberImageView = (ImageView) itemView.findViewById(getClauseNumberImageViewRes());
        titleTextView = (FermatTextView) itemView.findViewById(getTitleTextViewRes());
        confirmButton = (FermatButton) itemView.findViewById(getConfirmButtonRes());
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onConfirmClauseButtonClicked(clause);
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public FermatButton getConfirmButton() {
        return confirmButton;
    }

    public void bindData(NegotiationWrapper negotiationWrapper, ClauseInformation clause, int clausePosition) {
        this.negotiationInformation = negotiationWrapper.getNegotiationInfo();
        this.clause = clause;
        this.clausePosition = clausePosition;

        ClauseStatus status = negotiationWrapper.isClauseConfirmed(clause) ? clause.getStatus() : ClauseStatus.DRAFT;
        switch (status) {
            case ACCEPTED:
                containerCardView.setCardBackgroundColor(getColor(R.color.cbw_card_background_status_accepted));
                containerCardView.setClickable(false);
                confirmButton.setText(R.string.status_accepted);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                onAcceptedStatus();
                break;

            case CHANGED:
                containerCardView.setCardBackgroundColor(getColor(R.color.cbw_card_background_status_changed));
                containerCardView.setClickable(false);
                confirmButton.setText(R.string.status_changed);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                setChangedStatus();
                break;

            case DRAFT:
                containerCardView.setCardBackgroundColor(getColor(R.color.cbw_card_background_status_confirm));
                confirmButton.setText(R.string.status_confirm);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                onToConfirmStatus();
                break;
        }
    }

    public abstract void setViewResources(int titleRes, int positionImgRes, int... stringResources);

    protected abstract int getConfirmButtonRes();

    protected abstract int getClauseNumberImageViewRes();

    protected abstract int getTitleTextViewRes();

    public interface Listener {
        void onClauseClicked(Button triggerView, ClauseInformation clause, int clausePosition);

        void onConfirmClauseButtonClicked(ClauseInformation clause);
    }

    protected abstract void onAcceptedStatus();

    protected abstract void setChangedStatus();

    protected abstract void onToConfirmStatus();

    @SuppressWarnings("deprecation")
    protected int getColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return res.getColor(colorResId, null);
        else
            return res.getColor(colorResId);
    }
}
