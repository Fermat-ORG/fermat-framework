package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Yordin Alayn on 26.01.16.
 */
public class BrokerCurrencyQuotation {

    private List<BrokerCurrencyQuotationImpl> brokerCurrencyQuotationlist;

    public BrokerCurrencyQuotation(List<BrokerCurrencyQuotationImpl> brokerCurrencyQuotationlist){
        this.brokerCurrencyQuotationlist = brokerCurrencyQuotationlist;
    }

    public BrokerCurrencyQuotationImpl getBrokerCurrencyQuotation(String currencyOver, String currencyUnder){

        BrokerCurrencyQuotationImpl currencyQuotation = getQuotation(currencyOver,currencyUnder);

        if(currencyQuotation == null) {
            currencyQuotation = getQuotation(currencyUnder, currencyOver);
            if (currencyQuotation != null) {
                final BigDecimal one = new BigDecimal("1");
                BigDecimal exchangeRate = new BigDecimal(currencyQuotation.getExchangeRate());
                exchangeRate = one.divide(exchangeRate, 4, RoundingMode.HALF_UP);
                final String exchangeRateStr = DecimalFormat.getInstance().format(exchangeRate.doubleValue());
                currencyQuotation.setExchangeRate(exchangeRateStr);
            }
        }

        return currencyQuotation;
    }

    private BrokerCurrencyQuotationImpl getQuotation(String currencyAlfa, String currencyBeta) {

        for (BrokerCurrencyQuotationImpl item : brokerCurrencyQuotationlist)
            if ((item.getCurrencyOver().equals(currencyAlfa)) && (item.getCurrencyUnder().equals(currencyBeta))) return item;

        return null;
    }

}
