package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.MarketRateProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptocoinchartsServiceAPI;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptonatorServiceAPI;

import java.io.IOException;

/**
 * Created by francisco on 12/08/15.
 */
public class MarketPrice implements MarketRateProvider {
    Double marketExchangeRate = null;
    JsonService jsonService= new JsonService();
    CryptocoinchartsServiceAPI cryptocoinchartsServiceAPI = new CryptocoinchartsServiceAPI();
    CryptonatorServiceAPI cryptonatorServiceAPI = new CryptonatorServiceAPI();
        /**
     *
     * **/
    @Override
    public double getHistoricalExchangeRate(CryptoCurrency c, FiatCurrency f, long time) {
       String url = null;
            String crypto=c.getCode().toString();
            String fiat = f.getCode().toString();
            url = cryptocoinchartsServiceAPI.getTradingPair(crypto,fiat);
        try {
            marketExchangeRate = Double.valueOf(jsonService.getJSONFromUrl(url).getString("price"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marketExchangeRate;
    }


   /* @Override
    public double getHistoricalExchangeRate() {
        String url = cryptonatorServiceAPI.Ticker_btc_usdURL ;
        List<String> list;
        try {
            list= cryptonatorServiceAPI.getListTicker_btc_usdTicker(url);
            marketExchangeRate= Double.valueOf(list.get(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  marketExchangeRate;
    }*/
}









