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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 *Created by Yordin Alayn on 22.01.16.
 * Based in DateTimeViewHolder of Star_negotiation by nelson
 */
public class DateTimeViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private Button buttonDate;
    private Button buttonTime;
    private TextView descriptionTextView;
    private TextView youTimeZone;
    private TextView otheTimeZone;

    public DateTimeViewHolder(View itemView) {
        super(itemView);

        descriptionTextView = (TextView) itemView.findViewById(R.id.ccw_date_time_description_text);

        buttonDate = (Button) itemView.findViewById(R.id.ccw_date_value);
        buttonDate.setOnClickListener(this);
        buttonTime = (Button) itemView.findViewById(R.id.ccw_time_value);
        buttonTime.setOnClickListener(this);
        youTimeZone = (TextView) itemView.findViewById(R.id.ccw_text_you_time_zone);
        otheTimeZone = (TextView) itemView.findViewById(R.id.ccw_text_other_date);

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

        if(otherTimeZoneClause != null) {

            String otheTimeZoneValue = "Undefined";

            String youTimeZoneValue = TimeZone.getDefault().getID();
            otheTimeZoneValue = otherTimeZoneClause.getValue();

            if(!youTimeZoneValue.equals(otheTimeZoneValue)) {

//                String dateTime = dateFormat.format(timeInMillis) + " " + timeFormat.format(timeInMillis);
                youTimeZone.setVisibility(View.VISIBLE);
                otheTimeZone.setVisibility(View.VISIBLE);

                DateTimeZone dateTimeZoneOther = new DateTimeZone(otheTimeZoneValue, timeInMillis, "MM/dd/yyyy hh:mm a");

                youTimeZone.setText(youTimeZoneValue);
//                otheTimeZone.setText("Broker Date: " + getDateTimeOther(otheTimeZoneValue, dateTime) + " ( " + otheTimeZoneValue + " )");
                otheTimeZone.setText("Broker Date: " + dateTimeZoneOther.getDate() + " ( " + otheTimeZoneValue + " )");

            }


//            DateTimeZone dateTimeZoneDelivery = new DateTimeZone(TimeZone.getDefault().getID(),timeInMillis,"MM/dd/yyyy hh:mm a");
//            String dateTimeDelivery = dateTimeZoneDelivery.getDate();
//            String dateTimeDeliveryUTC = dateTimeZoneDelivery.getDateUTC();
//            String dateTimeToday    = dateTimeZoneDelivery.getDateTodayUTC();
//
//            System.out.print("\n *** TIME ZONE " +
//                    "\n - Date:" +dateTimeDelivery+
//                    "\n - Date UTC:" +dateTimeDeliveryUTC+
//                    "\n - DateToday: "+dateTimeToday+
//                    "\n - CompareTo: " + dateTimeDelivery.compareTo(dateTimeToday)+"\n");

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
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                break;
            case CHANGED:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                break;
            case CONFIRM:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                break;
        }
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
