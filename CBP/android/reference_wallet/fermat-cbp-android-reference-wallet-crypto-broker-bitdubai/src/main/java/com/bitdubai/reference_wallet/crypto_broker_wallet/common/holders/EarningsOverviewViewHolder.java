package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPairDetail;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Created by nelson on 27/01/16.
 */
public class EarningsOverviewViewHolder extends FermatViewHolder {

    private final FermatTextView differenceTextView;
    private final FermatTextView dateTextView;

    public EarningsOverviewViewHolder(View itemView) {
        super(itemView, 0);

        differenceTextView = (FermatTextView) itemView.findViewById(R.id.cbw_earning_value_difference_item);
        dateTextView = (FermatTextView) itemView.findViewById(R.id.cbw_earning_value_date_item);
    }

    public void bind(EarningsPairDetail data, TimeFrequency frequency, Currency earningCurrency) {
        final NumberFormat numberFormat = DecimalFormat.getInstance();

        final String diff = numberFormat.format(data.getAmount());
        final String currencyCode = earningCurrency.getCode();

        if (data.getAmount() > 0) {
            differenceTextView.setText(String.format("+ %s %s", diff, currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#39ab89"));
        } else if (data.getAmount() < 0) {
            differenceTextView.setText(String.format("%s %s", diff, currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#d14846"));
        } else {
            differenceTextView.setText(String.format("0.0 %s", currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#7c7c7c"));
        }

        CharSequence formattedDate;
        switch (frequency) {
            case MONTHLY:
                formattedDate = DateFormat.format("MMM yyyy", data.getToTimestamp());
                break;
            case YEARLY:
                formattedDate = DateFormat.format("yyyy", data.getToTimestamp());
                break;
            default:
                formattedDate = DateFormat.format("MMM yyyy", data.getToTimestamp());
                break;
        }

        dateTextView.setText(formattedDate);
    }
}
