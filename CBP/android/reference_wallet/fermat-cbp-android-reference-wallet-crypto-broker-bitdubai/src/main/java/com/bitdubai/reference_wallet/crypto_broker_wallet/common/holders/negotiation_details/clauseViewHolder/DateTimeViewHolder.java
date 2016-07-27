package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.util.DateTimeZone;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;

import java.util.Map;
import java.util.TimeZone;


/**
 * Created by Nelson Ramirez
 */
public class DateTimeViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private Button buttonDate;
    private Button buttonTime;
    private TextView descriptionTextView;
    private TextView youTimeZone;
    private TextView otheTimeZone;
    private View separatorLineUp;
    private View separatorLineDown;


    public DateTimeViewHolder(View itemView, int holderType) {
        super(itemView, holderType);

        descriptionTextView = (TextView) itemView.findViewById(R.id.cbw_date_time_description_text);

        buttonDate = (Button) itemView.findViewById(R.id.cbw_date_value);
        buttonDate.setOnClickListener(this);
        buttonTime = (Button) itemView.findViewById(R.id.cbw_time_value);
        buttonTime.setOnClickListener(this);
        youTimeZone = (TextView) itemView.findViewById(R.id.cbw_text_you_time_zone);
        otheTimeZone = (TextView) itemView.findViewById(R.id.cbw_text_other_date);
        separatorLineDown = itemView.findViewById(R.id.cbw_line_down);
        separatorLineUp = itemView.findViewById(R.id.cbw_line_up);
    }

    @Override
    public void bindData(NegotiationWrapper data, ClauseInformation clause, int position) {
        super.bindData(data, clause, position);

        java.text.DateFormat timeFormat = DateFormat.getTimeFormat(itemView.getContext());
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(itemView.getContext());

        long timeInMillis = Long.valueOf(clause.getValue());

        buttonTime.setText(timeFormat.format(timeInMillis));
        buttonDate.setText(dateFormat.format(timeInMillis));

        Map<ClauseType, ClauseInformation> clauses = data.getClauses();
        ClauseInformation otherTimeZoneClause = clauses.get(ClauseType.CUSTOMER_TIME_ZONE);

        if (otherTimeZoneClause != null) {
            String yourTimeZoneValue = TimeZone.getDefault().getID();
            String otherTimeZoneValue = otherTimeZoneClause.getValue();

            if (!yourTimeZoneValue.equals(otherTimeZoneValue)) {

                youTimeZone.setVisibility(View.VISIBLE);
                otheTimeZone.setVisibility(View.VISIBLE);

                DateTimeZone otherTimeZoneDate = new DateTimeZone(otherTimeZoneValue, timeInMillis, "MM/dd/yyyy hh:mm a");

                youTimeZone.setText(String.format("Time Zone: %1$s", yourTimeZoneValue));
                otheTimeZone.setText(String.format("Customer Date: %1$s (%2$s)", otherTimeZoneDate.getDate(), otherTimeZoneValue));
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
        return R.id.cbw_confirm_button;
    }

    @Override
    protected int getClauseNumberImageViewRes() {
        return R.id.cbw_clause_number;
    }

    @Override
    protected int getTitleTextViewRes() {
        return R.id.cbw_card_view_title;
    }

    @Override
    protected void onAcceptedStatus() {
        separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
        separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
        descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
        youTimeZone.setTextColor(getColor(R.color.card_title_color_status_accepted));
        otheTimeZone.setTextColor(getColor(R.color.card_title_color_status_accepted));
    }

    @Override
    protected void setChangedStatus() {
        separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
        separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
        descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
        youTimeZone.setTextColor(getColor(R.color.card_title_color_status_changed));
        otheTimeZone.setTextColor(getColor(R.color.card_title_color_status_changed));
    }

    @Override
    protected void onToConfirmStatus() {
        descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
    }

    /*private String getDateTimeOther(String timeZone, String date){

        String dateTime = "";
        String formatDate = "MM/dd/yyyy hh:mm a";

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(formatDate);
            sourceFormat.setTimeZone(TimeZone.getDefault());
            Date parsed = sourceFormat.parse(date);

            TimeZone tz = TimeZone.getTimeZone(timeZone);
            SimpleDateFormat destFormat = new SimpleDateFormat(formatDate);
            destFormat.setTimeZone(tz);

            dateTime = destFormat.format(parsed);

        } catch (ParseException e){
            System.out.print("error.");
        }

        return dateTime;

    }*/
}
