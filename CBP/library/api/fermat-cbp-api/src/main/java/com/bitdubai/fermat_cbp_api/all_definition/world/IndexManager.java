package com.bitdubai.fermat_cbp_api.all_definition.world;

import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;

import java.util.Collection;

/**
 * Created by jorge on 30-10-2015.
 */
public interface IndexManager<T extends Currency> {

    //Note: T is a generic which may only be a FiatCurrency or a CryptoCurrency.


    T getReferenceCurrency();
    Collection<T> getSupportedCurrencies();
    Index getCurrentIndex(T currency) throws CantGetIndexException;
    Index getIndexInDate(T currency, long timestamp) throws CantGetIndexException;
    Collection<Index> getQueriedIndexHistory(T currency);
}

