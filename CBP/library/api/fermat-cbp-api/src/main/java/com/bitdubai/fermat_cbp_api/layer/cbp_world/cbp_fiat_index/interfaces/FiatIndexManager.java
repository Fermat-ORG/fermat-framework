package com.bitdubai.fermat_cbp_api.layer.cbp_world.cbp_fiat_index.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.world.Index;
import com.bitdubai.fermat_cbp_api.layer.cbp_world.cbp_fiat_index.exceptions.CantGetIndexException;

import java.util.Collection;

/**
 * Created by jorge on 30-10-2015.
 */
public interface FiatIndexManager {

    FiatCurrencyType getReferenceCurrency();
    Collection<FiatCurrencyType> getSupportedCurrencies();
    Index getCurrentIndex(FiatCurrencyType currency) throws CantGetIndexException;
    Index getIndexInDate(FiatCurrencyType currency, long timestamp) throws CantGetIndexException;
    Collection<Index> getQueriedIndexHistory(FiatCurrencyType currency);
}

