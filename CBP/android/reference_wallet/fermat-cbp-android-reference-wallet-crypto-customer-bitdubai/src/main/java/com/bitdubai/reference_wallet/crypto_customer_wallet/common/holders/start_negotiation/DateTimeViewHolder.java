package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by nelson on 10/01/16.
 */
public class DateTimeViewHolder extends ClauseViewHolder {

    private View.OnClickListener listener;

    private Button buttonDate;
    private Button buttonTime;
    private TextView descriptionTextView;


    public DateTimeViewHolder(View itemView) {
        super(itemView);

        descriptionTextView = (TextView) itemView.findViewById(R.id.ccw_date_time_description_text);

        buttonDate = (Button) itemView.findViewById(R.id.ccw_date_value);
        buttonDate.setOnClickListener(listener);

        buttonTime = (Button) itemView.findViewById(R.id.ccw_time_value);
        buttonTime.setOnClickListener(listener);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation data, ClauseInformation clause) {

        java.text.DateFormat timeFormat = DateFormat.getTimeFormat(itemView.getContext());
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(itemView.getContext());

        long timeInMillis = Long.valueOf(clause.getValue());
        buttonTime.setText(timeFormat.format(timeInMillis));
        buttonDate.setText(dateFormat.format(timeInMillis));
    }

    public void setDateTimeListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
        descriptionTextView.setText(stringResources[0]);
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
}
