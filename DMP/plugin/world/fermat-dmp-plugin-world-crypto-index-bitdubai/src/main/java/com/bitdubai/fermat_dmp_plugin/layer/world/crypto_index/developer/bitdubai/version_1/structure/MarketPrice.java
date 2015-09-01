package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.MarketRateProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BterServiceAPI;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CoinwarzServiceAPI;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptocoinchartsServiceAPI;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptonatorServiceAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 12/08/15.
 */
public class MarketPrice implements MarketRateProvider {
    Double marketExchangeRate = null;
    JsonService jsonService= new JsonService();
    CryptocoinchartsServiceAPI cryptocoinchartsServiceAPI = new CryptocoinchartsServiceAPI();
    CryptonatorServiceAPI cryptonatorServiceAPI = new CryptonatorServiceAPI();
    CoinwarzServiceAPI coinwarzServiceAPI = new CoinwarzServiceAPI();
    BterServiceAPI bterServiceAPI = new BterServiceAPI();
    String url = null;
        /**
     *
     * **/
    @Override
    public double getHistoricalExchangeRate(CryptoCurrency c, FiatCurrency f, long time) {

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

    public List<String> getBestMarketPrice (String crypto, String fiat ){
        marketExchangeRate=null;
        url=null;
        List<String> priceList = new ArrayList<>();
        String url_1=cryptocoinchartsServiceAPI.getTradingPair(crypto,fiat);
        String url_2=cryptonatorServiceAPI.getCompleteTickerURL();
        String url_3=coinwarzServiceAPI.getUrlApi();
        String url_4=bterServiceAPI.getUrlApi(crypto,fiat);

        try {

           priceList.add(String.valueOf(jsonService.getJSONFromUrl(url_1).getString("price")));
           priceList.add(String.valueOf(jsonService.getJSONFromUrl(url_2).getJSONObject("ticker").getString("price")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return priceList;

    }

}









