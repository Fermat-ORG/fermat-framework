package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketRateStatisticsFragment extends AbstractFermatFragment {
    private String buy, sell, currencyPair, providerName;
    private IndexInfoSummary indexInfoSummary;
    private NumberFormat numberFormat = DecimalFormat.getInstance();

    public static MarketRateStatisticsFragment newInstance() {
        return new MarketRateStatisticsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.ccw_fragment_header_market_rate, container, false);

        FermatTextView buyPrice = (FermatTextView) rootView.findViewById(R.id.ccw_buy_price);
        FermatTextView sellPrice = (FermatTextView) rootView.findViewById(R.id.ccw_sell_price);
        FermatTextView currencies = (FermatTextView) rootView.findViewById(R.id.ccw_currencies);
        FermatTextView providerName = (FermatTextView) rootView.findViewById(R.id.ccw_provider_name);


        String buyAmount = buy.split(" ")[1];
        String buyCurrency = buy.split(" ")[0];
        String sellAmount = sell.split(" ")[1];
        String sellCurrency = sell.split(" ")[0];
        String buyWithFormat = fixFormat(buyAmount);
        String sellWithFormat = fixFormat(sellAmount);
        if (buyAmount.equals("0") && sellAmount.equals("0")) {
            providerName.setText(new StringBuilder().append(this.providerName).append(" is down").toString());
            providerName.setTextColor(ContextCompat.getColor(getActivity(), R.color.ccw_provider_is_down));
        } else {
            providerName.setText(this.providerName);
        }

        currencies.setText(currencyPair);
        buyPrice.setText(new StringBuilder().append(buyCurrency).append(" ").append(buyWithFormat).toString());
        sellPrice.setText(new StringBuilder().append(sellCurrency).append(" ").append(sellWithFormat).toString());

        return rootView;
    }

    public void bind(IndexInfoSummary indexInfo) {
        sell = indexInfo.getSalePriceAndCurrency();
        currencyPair = indexInfo.getCurrencyAndReferenceCurrency();
        buy = indexInfo.getPurchasePriceAndCurrency();
        providerName = indexInfo.getProviderName();
        indexInfoSummary = indexInfo;
    }

    private String fixFormat(String value) {

        try {
            if (compareLessThan1(value)) {
                numberFormat.setMaximumFractionDigits(8);
            } else {
                numberFormat.setMaximumFractionDigits(2);
            }
            return numberFormat.format(new BigDecimal(numberFormat.parse(value).toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }

    }

    private Boolean compareLessThan1(String value) {
        Boolean lessThan1 = true;
        try {
            if (BigDecimal.valueOf(numberFormat.parse(value).doubleValue()).
                    compareTo(BigDecimal.ONE) == -1) {
                lessThan1 = true;
            } else {
                lessThan1 = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lessThan1;
    }
}
