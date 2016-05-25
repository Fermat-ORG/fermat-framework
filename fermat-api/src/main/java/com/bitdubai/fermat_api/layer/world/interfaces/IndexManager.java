package com.bitdubai.fermat_api.layer.world.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;

import java.util.Collection;

/**
 * Created by jorge on 30-10-2015.
 */
public interface IndexManager<C, I> extends FermatManager {

    //Note: T is a generic which may only be a FiatCurrency or a CryptoCurrency.


    C getReferenceCurrency();
    Collection<C> getSupportedCurrencies();
    I getCurrentIndex(C currency) throws CantGetIndexException;
    Collection<I> getIndexListFromDate(C currency, long timestamp) throws CantGetIndexException;
    Collection<I> getQueriedIndexHistory(C currency);
}

