package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

/**
 * Created by Yordin Alayn on 25.01.16.
 */
public class BrokerCurrencyQuotationImpl {

    private String currencyOver;

    private String currencyUnder;

    private String exchangeRate;

    public BrokerCurrencyQuotationImpl(
            String currencyOver,
            String currencyUnder,
            String exchangeRate
    ) {
        this.currencyOver = currencyOver;
        this.currencyUnder = currencyUnder;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyOver() {
        return this.currencyOver;
    }

    public String getCurrencyUnder() {
        return this.currencyUnder;
    }

    public String getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

}
