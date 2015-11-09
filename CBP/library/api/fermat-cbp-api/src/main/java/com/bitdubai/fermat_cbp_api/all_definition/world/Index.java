package com.bitdubai.fermat_cbp_api.all_definition.world;

/**
 * Created by jorge on 30-10-2015.
 */
public interface Index<T extends Currency> {

    //Note: T is a generic which may only be a FiatCurrency or a CryptoCurrency.

    T getCurrency();
    T getReferenceCurrency();
    double getSalePrice();
    double getPurchasePrice();
    long getTimestamp();
}
