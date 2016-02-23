package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketRateStatisticsFragment extends AbstractFermatFragment {
    private String buy, sell, currencyPair, providerName;

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

        providerName.setText(this.providerName);
        currencies.setText(currencyPair);
        buyPrice.setText(buy);
        sellPrice.setText(sell);

        return rootView;
    }

    public void bind(IndexInfoSummary indexInfo) {
        sell = indexInfo.getSalePriceAndCurrency();
        currencyPair = indexInfo.getCurrencyAndReferenceCurrency();
        buy = indexInfo.getPurchasePriceAndCurrency();
        providerName = indexInfo.getProviderName();
    }
}
