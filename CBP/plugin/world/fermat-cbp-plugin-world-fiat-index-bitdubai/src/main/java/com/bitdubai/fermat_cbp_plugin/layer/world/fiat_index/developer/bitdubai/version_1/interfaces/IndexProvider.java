package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */
public interface IndexProvider {

    FiatIndexImpl getCurrentIndex(FiatCurrency currency) throws CantGetIndexException;

}
