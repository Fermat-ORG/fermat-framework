package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

//import org.json.JSONObject;

/**
 * Created by francisco on 15/08/15.
 */
public interface MarketRateProvider {

    double getHistoricalExchangeRate(CryptoCurrency c, FiatCurrency f , long time);




}
