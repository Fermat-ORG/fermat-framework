package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;

/**
 * Created by jorge on 30-10-2015.
 * Modified by Alex on 11/8/2015.
 */
public class FiatIndexImpl implements FiatIndex {

    private FiatCurrency currency;
    private FiatCurrency referenceCurrency;
    double salePrice;
    double purchasePrice;
    long timestamp;

    public FiatIndexImpl(FiatCurrency currencyType, FiatCurrency referenceCurrency, double salePrice, double purchasePrice, long timestamp) {
        this.currency = currencyType;
        this.referenceCurrency = referenceCurrency;
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
        this.timestamp = timestamp;
    }

    @Override
    public FiatCurrency getCurrency() {
        return currency;
    }

    @Override
    public FiatCurrency getReferenceCurrency() {
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
    public long getTimestamp() {
        return timestamp;
    }
}
