package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarketRateStatisticsFragment extends FermatWalletFragment {
    private String buy, sell, currencyPair;

    public static MarketRateStatisticsFragment newInstance() {
        return new MarketRateStatisticsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.cbw_fragment_header_market_rate, container, false);

        FermatTextView buyPrice = (FermatTextView) rootView.findViewById(R.id.ccw_buy_price);
        FermatTextView sellPrice = (FermatTextView) rootView.findViewById(R.id.ccw_sell_price);
        FermatTextView currencies = (FermatTextView) rootView.findViewById(R.id.ccw_currencies);

        currencies.setText(currencyPair);
        String buyText = rootView.getResources().getString(R.string.buy_text_and_price, buy);
        buyPrice.setText(buyText);
        String sellText = rootView.getResources().getString(R.string.sell_text_and_price, sell);
        sellPrice.setText(sellText);

        return rootView;
    }

    public void bind(IndexInfoSummary indexInfo) {
        sell = indexInfo.getSalePriceAndCurrency();
        currencyPair = indexInfo.getCurrencyAndReferenceCurrency();
        buy = indexInfo.getPurchasePriceAndCurrency();
    }
}
