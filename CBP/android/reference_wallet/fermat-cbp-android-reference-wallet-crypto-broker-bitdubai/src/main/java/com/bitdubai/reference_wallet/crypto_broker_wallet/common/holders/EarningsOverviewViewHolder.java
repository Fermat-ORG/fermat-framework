package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningsDetailData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.EarningCurrencyCalendarRelationship;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


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

    public void bind(EarningsDetailData data, TimeFrequency frequency, Currency earningCurrency) {
        final NumberFormat numberFormat = DecimalFormat.getInstance();
        final String currencyCode = earningCurrency.getCode();

        double amount = data.getAmount();

        if(earningCurrency.getType() == CurrencyTypes.CRYPTO && CryptoCurrency.BITCOIN.getCode().equals(currencyCode))
            amount = BitcoinConverter.convert(amount, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);

        final String diff = numberFormat.format(amount);

        if (amount > 0) {
            differenceTextView.setText(String.format("+ %s %s", diff, currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#39ab89"));
        } else if (amount < 0) {
            differenceTextView.setText(String.format("%s %s", diff, currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#d14846"));
        } else {
            differenceTextView.setText(String.format("0.0 %s", currencyCode));
            differenceTextView.setTextColor(Color.parseColor("#7c7c7c"));
        }

        CharSequence formattedDate;
        switch (frequency) {
            case MONTHLY:
                formattedDate = DateFormat.format("MMM yyyy", getDate(data.getRelationship()));
                break;
            case YEARLY:
                formattedDate = DateFormat.format("yyyy", getDate(data.getRelationship()));
                break;
            default:
                formattedDate = DateFormat.format("dd MMM yyyy", getDate(data.getRelationship()));
                break;
        }

        dateTextView.setText(formattedDate);
    }

    private Calendar getDate(EarningCurrencyCalendarRelationship data) {

        return new GregorianCalendar(
                data.getYear(),
                data.getMonth(),
                data.getDay()
        );
    }
}
