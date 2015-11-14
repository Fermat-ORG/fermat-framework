package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.world.Index;
import com.bitdubai.fermat_cbp_api.layer.cbp_world.cbp_fiat_index.exceptions.CantGetIndexException;

/**
 * Created by Alex on 11/2/2015.
 */
public interface IndexProvider {

    Index getCurrentIndex(FiatCurrency currency) throws CantGetIndexException;

}
