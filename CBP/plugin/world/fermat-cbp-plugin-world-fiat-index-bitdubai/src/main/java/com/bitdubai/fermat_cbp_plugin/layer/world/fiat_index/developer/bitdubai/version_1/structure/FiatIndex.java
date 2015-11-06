package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.world.Index;

/**
 * Created by jorge on 30-10-2015.
 */
public class FiatIndex implements Index {

    private FiatCurrencyType currencyType;
    private FiatCurrencyType referenceCurrency;
    double salePrice;
    double purchasePrice;
    long timestamp;

    public FiatIndex(FiatCurrencyType currencyType) {
        this.currencyType = currencyType;
        this.referenceCurrency = FiatCurrencyType.US_DOLLAR;
    }


    public FiatIndex(FiatCurrencyType currencyType, FiatCurrencyType referenceCurrency) {
        this.currencyType = currencyType;
        this.referenceCurrency = referenceCurrency;
    }

    @Override
    public void setCurrency(FiatCurrencyType currency) {
        this.currencyType = currency;
    }

    @Override
    public void setReferenceCurrency(FiatCurrencyType currency) {
        this.referenceCurrency = currency;
    }

    @Override
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public void setTimeStamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public FiatCurrencyType getCurrency() {
        return currencyType;
    }

    @Override
    public FiatCurrencyType getReferenceCurrency() {
        return referenceCurrency;
    }

    @Override
    public double getSalePrice() {
        return salePrice;
    }

    @Override
    public double getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public long getTimeStamp() {
        return timestamp;
    }
}
