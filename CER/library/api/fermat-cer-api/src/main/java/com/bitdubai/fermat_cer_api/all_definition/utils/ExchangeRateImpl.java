package com.bitdubai.fermat_cer_api.all_definition.utils;


import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;

import java.io.Serializable;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class ExchangeRateImpl implements ExchangeRate, Serializable {

    private Currency fromCurrency;
    private Currency toCurrency;
    double salePrice;
    double purchasePrice;
    long timestamp;

    public ExchangeRateImpl(Currency fromCurrency, Currency toCurrency, double salePrice, double purchasePrice, long timestamp) {

        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
        this.timestamp = timestamp;
    }


    @Override
    public Currency getFromCurrency() {
        return this.fromCurrency;
    }

    @Override
    public Currency getToCurrency() {
        return this.toCurrency;
    }

    @Override
    public double getSalePrice() {
        return this.salePrice;
    }

    @Override
    public double getPurchasePrice() {
        return this.purchasePrice;
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

}