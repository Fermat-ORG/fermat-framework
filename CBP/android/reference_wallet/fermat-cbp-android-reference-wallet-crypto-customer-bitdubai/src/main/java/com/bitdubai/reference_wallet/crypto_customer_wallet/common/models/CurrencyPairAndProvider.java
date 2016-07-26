package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.util.UUID;


/**
 * Created by nelson on 05/02/16.
 */
public class CurrencyPairAndProvider {


    private Currency currencyFrom;
    private Currency currencyTo;
    private UUID providerId;
    private String providerName;

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

    public UUID getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(currencyFrom.getCode()).append("/").append(currencyTo.getCode()).append(" - ").append(providerName).toString();
    }
}
