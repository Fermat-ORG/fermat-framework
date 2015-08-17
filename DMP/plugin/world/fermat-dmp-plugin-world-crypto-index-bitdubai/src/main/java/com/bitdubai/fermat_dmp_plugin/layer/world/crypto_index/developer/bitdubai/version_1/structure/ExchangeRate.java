package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptocoinchartsServiceAPI;

/**
 * Created by francisco on 12/08/15.
 */
public class ExchangeRate {

    MarketPrice marketPrice;
    String jsonField;
    CryptoCurrency c;
    CryptoCurrency l;
    FiatCurrency f;

    long time;
    CryptocoinchartsServiceAPI cryptocoinchartsServiceAPI = new CryptocoinchartsServiceAPI();

    public ExchangeRate() throws InvalidParameterException {
        f = f.getByCode("USD");
        c = c.getByCode("BTC");
        l=c.getByCode("LTC");

    }

    /**get price pair Fiat_Crypto**/
    public double getCryptocoinchar_PairsPriceFiatCrypto() throws InvalidParameterException {
        String url=cryptocoinchartsServiceAPI.getTradingPair();
        String pair=marketPrice.pairFiatCrypto(c,f);
        jsonField="price";
        return marketPrice.getMarketExchangeRate(pair,time,url,jsonField);

    }
    /**get price pair Crypto_Fiat**/
    public double getCryptocoinchar_PairsPriceCryptoFiat() throws InvalidParameterException {
        String url=cryptocoinchartsServiceAPI.getTradingPair();
        String pair=marketPrice.pairCryptoFiat(c, f);
                jsonField="price";
        return marketPrice.getMarketExchangeRate(pair,time,url,jsonField);
    }
    /**
     * REVISAR BIEN ESTA PARTE !!!!!!!!!!!!!!!
     * **/
    public double getCryptocoinchar_PairsPriceCryptoCrypto() throws InvalidParameterException {
        String url=cryptocoinchartsServiceAPI.getTradingPair();
        String pair=marketPrice.pairCryptoCrypto(c,c);
        jsonField="price";
        return marketPrice.getMarketExchangeRate(pair,time,url,jsonField);
    }
}