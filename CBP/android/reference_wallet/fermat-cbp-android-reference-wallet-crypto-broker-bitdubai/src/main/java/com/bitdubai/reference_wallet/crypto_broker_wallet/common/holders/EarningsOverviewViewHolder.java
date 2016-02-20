package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningTestData;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Created by nelson on 27/01/16.
 */
public class EarningsOverviewViewHolder extends FermatViewHolder {

    private final FermatTextView differenceTextView;
    private final FermatTextView dateTextView;

    public EarningsOverviewViewHolder(View itemView) {
        super(itemView);

        differenceTextView = (FermatTextView) itemView.findViewById(R.id.cbw_earning_value_difference_item);
        dateTextView = (FermatTextView) itemView.findViewById(R.id.cbw_earning_value_date_item);
    }

    public void bind(EarningTestData data, int timeField) {
        final NumberFormat numberFormat = DecimalFormat.getInstance();

        final String diff = numberFormat.format(data.getDifference());
        final String currencyCode = data.getCurrency().getCode();

        if (data.getDifference() > 0) {
            differenceTextView.setText(String.format("+ %s %s", diff, currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#39ab89"));
        } else if (data.getDifference() < 0) {
            differenceTextView.setText(String.format("%s %s", diff, currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#d14846"));
        } else {
            differenceTextView.setText(String.format("0.0 %s", currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#7c7c7c"));
        }

        CharSequence formattedDate;
        switch (timeField) {
            case Calendar.MONTH:
                formattedDate = DateFormat.format("MMM yyyy", data.getTimestamp());
                break;
            case Calendar.DATE:
                formattedDate = DateFormat.format("dd MMM yyyy", data.getTimestamp());
                break;
            default:
                formattedDate = DateFormat.format("MMM yyyy", data.getTimestamp());
                break;
        }

        dateTextView.setText(formattedDate);
    }
}
