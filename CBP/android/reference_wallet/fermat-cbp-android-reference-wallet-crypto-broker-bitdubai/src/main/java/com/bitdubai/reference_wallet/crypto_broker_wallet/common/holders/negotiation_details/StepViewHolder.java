package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details;

import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.NegotiationDetailsAdapter;


public abstract class StepViewHolder extends FermatViewHolder {

    private final Resources res;

    protected NegotiationStepStatus actualStatus;
    protected boolean valuesHasChanged;
    protected int itemPosition;

    protected Button confirmButton;
    protected ImageView clauseNumberImageView;
    protected TextView titleTextView;
    public NegotiationDetailsAdapter adapter;

    public StepViewHolder(View itemView, NegotiationDetailsAdapter adapter) {
        super(itemView);

        this.adapter = adapter;
        res = itemView.getResources();

        actualStatus = NegotiationStepStatus.CONFIRM;
        valuesHasChanged = false;

        configClauseViews(itemView);
    }

    private void configClauseViews(View itemView) {
        titleTextView = (TextView) itemView.findViewById(R.id.cbw_card_view_title);
        clauseNumberImageView = (ImageView) itemView.findViewById(R.id.cbw_clause_number);

        confirmButton = (Button) itemView.findViewById(R.id.cbw_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valuesHasChanged && actualStatus.equals(NegotiationStepStatus.CONFIRM))
                    actualStatus = NegotiationStepStatus.CHANGED;
                else if (!valuesHasChanged && actualStatus.equals(NegotiationStepStatus.CONFIRM))
                    actualStatus = NegotiationStepStatus.ACCEPTED;

                modifyData(actualStatus);
            }
        });
    }

    public void bind(int stepNumber) {
        this.itemPosition = stepNumber - 1;

        clauseNumberImageView.setImageResource(getClauseNumberImageRes(stepNumber));

        NegotiationStep step = adapter.getDataSetItem(itemPosition);
        setStatus(step.getStatus());
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
                containerCardView.setCardBackgroundColor(getColor(R.color.cbw_card_background_status_confirm));
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

    protected int getClauseNumberImageRes(int clauseNumber) {
        switch (clauseNumber) {
            case 1:
                return R.drawable.bg_detail_number_01;
            case 2:
                return R.drawable.bg_detail_number_02;
            case 3:
                return R.drawable.bg_detail_number_03;
            case 4:
                return R.drawable.bg_detail_number_04;
            case 5:
                return R.drawable.bg_detail_number_05;
            case 6:
                return R.drawable.bg_detail_number_06;
            case 7:
                return R.drawable.bg_detail_number_07;
            case 8:
                return R.drawable.bg_detail_number_08;
            default:
                return R.drawable.bg_detail_number_09;
        }
    }

    protected void modifyData(NegotiationStepStatus status) {
        setStatus(status);
    }
}
