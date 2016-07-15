package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.PaymentConstants;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import org.bitcoin.protocols.payments.Protos;

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
    private NumberFormat numberFormat= DecimalFormat.getInstance();

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


        if(indexInfoSummary.getExchangeRateData().getToCurrency().getType().name().equals(PaymentType.CRYPTO_MONEY.getCode())){
            numberFormat.setMaximumFractionDigits(8);
        }else{
            numberFormat.setMaximumFractionDigits(2);
        }

        try {

            String buyAmount=buy.split(" ")[1];
            String buyCurrency=buy.split(" ")[0];
            String sellAmount=sell.split(" ")[1];
            String sellCurrency=sell.split(" ")[0];
            String buyWithFormat= numberFormat.format(numberFormat.parse(buyAmount));
            String sellWithFormat= numberFormat.format(numberFormat.parse(sellAmount));
            providerName.setText(this.providerName);
            currencies.setText(currencyPair);
            buyPrice.setText(buyCurrency+" "+buyWithFormat);
            sellPrice.setText(sellCurrency+" "+sellWithFormat);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        return rootView;
    }

    public void bind(IndexInfoSummary indexInfo) {
        sell = indexInfo.getSalePriceAndCurrency();
        currencyPair = indexInfo.getCurrencyAndReferenceCurrency();
        buy = indexInfo.getPurchasePriceAndCurrency();
        providerName = indexInfo.getProviderName();
        indexInfoSummary=indexInfo;
    }
}
