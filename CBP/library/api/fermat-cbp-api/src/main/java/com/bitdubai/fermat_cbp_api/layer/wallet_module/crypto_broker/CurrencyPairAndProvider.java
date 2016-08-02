package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.io.Serializable;
import java.util.UUID;


public class CurrencyPairAndProvider implements Serializable{


    private Currency currencyFrom;
    private Currency currencyTo;
    private String providerName;
    private UUID providerId;

    public CurrencyPairAndProvider(Currency currencyFrom, Currency currencyTo, UUID providerId, String providerName) {

        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    public String getProviderName() {
        return providerName;
    }

    @Override
    public String toString() {
        return currencyFrom.getCode() + "/" + currencyTo.getCode() + " - " + providerName;
    }

    public UUID getProviderId() {
        return providerId;
    }
}