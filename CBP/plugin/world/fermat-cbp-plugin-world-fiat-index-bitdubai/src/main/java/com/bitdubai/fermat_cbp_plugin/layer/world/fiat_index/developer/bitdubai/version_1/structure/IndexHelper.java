package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.world.Index;
import com.bitdubai.fermat_cbp_api.layer.cbp_world.cbp_fiat_index.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.enums.FiatIndexProviders;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;

/**
 * Created by Alex on 11/2/2015.
 */
public class IndexHelper {


    public Index getCurrentIndex(FiatCurrencyType currency) throws CantGetIndexException {
        String currencyCode = currency.getCode();
        IndexProvider ip = FiatIndexProviders.valueOf(currencyCode).getProviderInstance();

        return ip.getCurrentIndex(currency);
    }

}
