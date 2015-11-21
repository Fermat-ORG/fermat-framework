package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.enums.FiatIndexProviders;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */
public class IndexHelper {


    public FiatIndexImpl getCurrentIndex(FiatCurrency currency) throws CantGetIndexException {
        String currencyCode = currency.getCode();
        IndexProvider ip = FiatIndexProviders.valueOf(currencyCode).getProviderInstance();

        return ip.getCurrentIndex(currency);
    }

}
