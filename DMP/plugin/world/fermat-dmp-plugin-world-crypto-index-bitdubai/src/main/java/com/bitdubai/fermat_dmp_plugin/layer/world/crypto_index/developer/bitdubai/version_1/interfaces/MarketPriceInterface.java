package com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

/**
 * Created by francisco on 15/08/15.
 */
public interface MarketPriceInterface {
    /**
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param time
     * @return
     */
    double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency,
                                     FiatCurrency fiatCurrency,
                                     long time);

}
