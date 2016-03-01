package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;

/**
 * Created by nelson on 17/02/16.
 */
public class ExpirationTimeViewHolder extends FermatViewHolder implements View.OnClickListener {

    private Resources res;
    private CardView containerCardView;

    private ImageView numberImageView;
    private FermatButton confirmButton;
    private FermatButton buttonDate;
    private FermatButton buttonTime;
    private FermatTextView titleTextView;
    private FermatTextView descriptionTextView;

    private Listener listener;
    private NegotiationWrapper negotiation;

    public ExpirationTimeViewHolder(View itemView, int holderType) {
        super(itemView, holderType);
        res = itemView.getResources();

        containerCardView = (CardView) this.itemView;
        numberImageView = (ImageView) itemView.findViewById(R.id.cbw_clause_number);
        titleTextView = (FermatTextView) itemView.findViewById(R.id.cbw_card_view_title);
        confirmButton = (FermatButton) itemView.findViewById(R.id.cbw_confirm_button);
        confirmButton.setOnClickListener(this);

        descriptionTextView = (FermatTextView) itemView.findViewById(R.id.cbw_date_time_description_text);
        buttonDate = (FermatButton) itemView.findViewById(R.id.cbw_date_value);
        buttonDate.setOnClickListener(this);
        buttonTime = (FermatButton) itemView.findViewById(R.id.cbw_time_value);
        buttonTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (listener != null) {
            if (view.getId() == R.id.cbw_confirm_button)
                listener.onExpirationDatetimeConfirmButtonClicked();
            else
                listener.onExpirationDatetimeValueClicked((FermatButton) view);
        }
    }
    public void HideButtons() {
        confirmButton.setVisibility(View.GONE);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void bindData(NegotiationWrapper negotiation, int positionImgRes) {
        this.negotiation = negotiation;

        java.text.DateFormat timeFormat = DateFormat.getTimeFormat(itemView.getContext());
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(itemView.getContext());

        final CustomerBrokerNegotiationInformation negotiationInformation = negotiation.getNegotiationInfo();
        final ClauseStatus status = negotiation.isExpirationTimeConfirmed() ? negotiation.getExpirationTimeStatus() : ClauseStatus.DRAFT;
        final long expirationDate = negotiationInformation.getNegotiationExpirationDate();

        buttonTime.setText(timeFormat.format(expirationDate));
        buttonDate.setText(dateFormat.format(expirationDate));

        titleTextView.setText(R.string.expiration_date_title);
        descriptionTextView.setText(R.string.expiration_date_text);
        numberImageView.setImageResource(positionImgRes);

        switch (status) {
            case ACCEPTED:
                containerCardView.setCardBackgroundColor(getColor(R.color.cbw_card_background_status_accepted));
                containerCardView.setClickable(false);
                confirmButton.setText(R.string.status_accepted);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                break;

            case CHANGED:
                containerCardView.setCardBackgroundColor(getColor(R.color.cbw_card_background_status_changed));
                containerCardView.setClickable(false);
                confirmButton.setText(R.string.status_changed);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                break;

            case DRAFT:
                containerCardView.setCardBackgroundColor(getColor(R.color.cbw_card_background_status_confirm));
                confirmButton.setText(R.string.status_confirm);
                titleTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                break;
        }
    }

    public interface Listener {
        void onExpirationDatetimeValueClicked(Button triggerView);

        void onExpirationDatetimeConfirmButtonClicked();
    }


    @SuppressWarnings("deprecation")
    protected int getColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return res.getColor(colorResId, null);
        else
            return res.getColor(colorResId);
    }


}
