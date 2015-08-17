package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import org.json.JSONObject;

/**
 * Created by francisco on 15/08/15.
 */
public interface MarketRateProvider {

    double getMarketExchangeRate(String pair, long time,String url, String jsonField);
    JSONObject jsonInterface(String url);
    /**Interface Pair Crypto Fiat**/
    String pairCryptoFiat (CryptoCurrency c, FiatCurrency f);
    /**Interface Pair Fiat Crypto**/
    String pairFiatCrypto (CryptoCurrency c, FiatCurrency f);
    /**Interface Pair Crypto Crypto**/
    String pairCryptoCrypto(CryptoCurrency c1, CryptoCurrency c2);
    /**Interface Pair Fiat Fiat**/
    String pairFiatFiat(FiatCurrency f1, FiatCurrency f2);
}
