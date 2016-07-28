package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.all_definition.util.DateTimeZone;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.util.Map;
import java.util.TimeZone;


/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in DateTimeViewHolder of Star_negotiation by nelson
 */
public class DateTimeViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private Button buttonDate;
    private Button buttonTime;
    private TextView descriptionTextView;
    private TextView youTimeZone;
    private TextView otheTimeZone;
    private View separatorLineUp;
    private View separatorLineDown;

    public DateTimeViewHolder(View itemView) {
        super(itemView);

        descriptionTextView = (TextView) itemView.findViewById(R.id.ccw_date_time_description_text);

        buttonDate = (Button) itemView.findViewById(R.id.ccw_date_value);
        buttonDate.setOnClickListener(this);
        buttonTime = (Button) itemView.findViewById(R.id.ccw_time_value);
        buttonTime.setOnClickListener(this);
        youTimeZone = (TextView) itemView.findViewById(R.id.ccw_text_you_time_zone);
        otheTimeZone = (TextView) itemView.findViewById(R.id.ccw_text_other_date);
        separatorLineDown = itemView.findViewById(R.id.ccw_line_down);
        separatorLineUp = itemView.findViewById(R.id.ccw_line_up);

    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation data, ClauseInformation clause, int position) {
        super.bindData(data, clause, position);

        java.text.DateFormat timeFormat = DateFormat.getTimeFormat(itemView.getContext());
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(itemView.getContext());

        long timeInMillis = Long.valueOf(clause.getValue());

        buttonTime.setText(timeFormat.format(timeInMillis));
        buttonDate.setText(dateFormat.format(timeInMillis));

        Map<ClauseType, ClauseInformation> clauses = data.getClauses();
        ClauseInformation otherTimeZoneClause = clauses.get(ClauseType.BROKER_TIME_ZONE);

        if (otherTimeZoneClause != null) {
            String yourTimeZoneValue = TimeZone.getDefault().getID();
            String otherTimeZoneValue = otherTimeZoneClause.getValue();

            if (!yourTimeZoneValue.equals(otherTimeZoneValue)) {

                youTimeZone.setVisibility(View.VISIBLE);
                otheTimeZone.setVisibility(View.VISIBLE);

                DateTimeZone otherTimeZoneDate = new DateTimeZone(otherTimeZoneValue, timeInMillis, "MM/dd/yyyy hh:mm a");

                youTimeZone.setText(String.format("Time Zone12345678: %1$s", yourTimeZoneValue));
                otheTimeZone.setText(String.format("Broker Date: %1$s (%2$s)", otherTimeZoneDate.getDate(), otherTimeZoneValue));
            }
        }

    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
        descriptionTextView.setText(stringResources[0]);

    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseClicked((Button) view, clause, clausePosition);
    }

    @Override
    protected int getConfirmButtonRes() {
        return R.id.ccw_confirm_button;
    }

    @Override
    protected int getClauseNumberImageViewRes() {
        return R.id.ccw_clause_number;
    }

    @Override
    protected int getTitleTextViewRes() {
        return R.id.ccw_card_view_title;
    }

    @Override
    public void setStatus(NegotiationStepStatus stepStatus) {
        super.setStatus(stepStatus);

        switch (stepStatus) {
            case ACCEPTED:
                separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
                separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
                youTimeZone.setTextColor(getColor(R.color.card_title_color_status_accepted));
                otheTimeZone.setTextColor(getColor(R.color.card_title_color_status_accepted));
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                break;
            case CHANGED:
                separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_changed));
                separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_changed));
                youTimeZone.setTextColor(getColor(R.color.card_title_color_status_changed));
                otheTimeZone.setTextColor(getColor(R.color.card_title_color_status_changed));
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                break;
            case CONFIRM:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                break;
        }
    }
}
