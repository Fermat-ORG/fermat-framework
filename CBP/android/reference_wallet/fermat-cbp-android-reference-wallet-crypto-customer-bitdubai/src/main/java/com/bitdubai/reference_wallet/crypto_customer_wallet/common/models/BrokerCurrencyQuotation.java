package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 * Created by Yordin Alayn on 26.01.16.
 */
public class BrokerCurrencyQuotation {

    private List<IndexInfoSummary> marketExchangeRates;

    public BrokerCurrencyQuotation(List<IndexInfoSummary> marketExchangeRates) {
        this.marketExchangeRates = marketExchangeRates;
    }

    public String getExchangeRate(String currencyOver, String currencyUnder) {

        ExchangeRate currencyQuotation = getQuotation(currencyOver, currencyUnder);
        String exchangeRateStr=defaultValue();

        if (currencyQuotation == null) {
            currencyQuotation = getQuotation(currencyUnder, currencyOver);
            if (currencyQuotation != null) {
                BigDecimal exchangeRate = new BigDecimal(currencyQuotation.getSalePrice());
                exchangeRate = (new BigDecimal(1)).divide(exchangeRate, 8, RoundingMode.HALF_UP);
                exchangeRateStr = DecimalFormat.getInstance().format(exchangeRate.doubleValue());
            }
        } else {
            BigDecimal exchangeRate = new BigDecimal(currencyQuotation.getSalePrice());
            exchangeRateStr = DecimalFormat.getInstance().format(exchangeRate.doubleValue());
        }

        return exchangeRateStr;
    }



    private ExchangeRate getQuotation(String currencyAlfa, String currencyBeta) {

        if (marketExchangeRates != null)
            for (IndexInfoSummary item : marketExchangeRates) {
                final ExchangeRate exchangeRateData = item.getExchangeRateData();
                final String toCurrency = exchangeRateData.getToCurrency().getCode();
                final String fromCurrency = exchangeRateData.getFromCurrency().getCode();

                if (toCurrency.equals(currencyAlfa) && fromCurrency.equals(currencyBeta))
                    return exchangeRateData;
            }

        return null;
    }


    String defaultValue(){
        DecimalFormatSymbols symbols =((DecimalFormat)DecimalFormat.getInstance()).getDecimalFormatSymbols();
        if(symbols.getDecimalSeparator()=='.'){
            return "0.0";
        }else{
            return "0,0";
        }
    }

}
