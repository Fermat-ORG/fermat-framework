package com.bitdubai.fermat_cbp_api.layer.cbp_world.cbp_fiat_index.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.world.Index;
import com.bitdubai.fermat_cbp_api.layer.cbp_world.cbp_fiat_index.exceptions.CantGetIndexException;

import java.util.Collection;

/**
 * Created by jorge on 30-10-2015.
 */
public interface FiatIndexManager {
    FermatEnum getReferenceCurrency();
    Collection<FermatEnum> getSupportedCurrencies();
    Index getCurrentIndex(FermatEnum currency) throws CantGetIndexException;
    Index getIndexInDate(FermatEnum currency, long timestamp) throws CantGetIndexException;
    Collection<Index> getQueriedIndexHistory(FermatEnum currency);
}
