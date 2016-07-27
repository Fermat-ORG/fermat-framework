package com.bitdubai.fermat_api.layer.world.interfaces;

/**
 * Created by jorge on 30-10-2015.
 */
public interface Index<C extends Currency> {

    //Note: T is a generic which may only be a FiatCurrency or a CryptoCurrency.

    C getCurrency();

    C getReferenceCurrency();

    double getSalePrice();

    double getPurchasePrice();

    long getTimestamp();

    String getProviderDescription();
}
