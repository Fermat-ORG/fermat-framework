package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptocoinchartsServiceAPI;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptonatorServiceAPI;

import java.util.ArrayList;
import java.util.List;

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
    CryptonatorServiceAPI cryptonatorServiceAPI = new CryptonatorServiceAPI();

    public ExchangeRate() throws InvalidParameterException {
        f = f.getByCode("USD");
        c = c.getByCode("BTC");
        l=c.getByCode("LTC");

    }
/**-------------------------------Cryptocoinchar API -------------------------------**/
    /**Cryptocoinchar --get price pair Fiat_Crypto**/
    public double getCryptocoinchar_PairsPriceFiatCrypto() throws InvalidParameterException {
        String url=cryptocoinchartsServiceAPI.getTradingPair();
        String pair=marketPrice.pairFiatCrypto(c, f);
        jsonField="price";
        return marketPrice.getMarketExchangeRate(pair,time,url,jsonField);

    }
    /**Cryptocoinchar --get price pair Crypto_Fiat**/
    public double getCryptocoinchar_PairsPriceCryptoFiat() throws InvalidParameterException {
        String url=cryptocoinchartsServiceAPI.getTradingPair();
        String pair=marketPrice.pairCryptoFiat(c, f);
                jsonField="price";
        return marketPrice.getMarketExchangeRate(pair,time,url,jsonField);
    }

   public List<String> getCryptocoinchar_tradingPairs(){
        String url=cryptocoinchartsServiceAPI.getManyTradingPair();
        List<String> list;
       list=cryptocoinchartsServiceAPI.getListTradingPair(url);

        return list;

   }
    public List<String> getCryptocoinchar_ListCoin(){
        String url=cryptocoinchartsServiceAPI.getListCoinURL();
        List<String> list;
        list=cryptocoinchartsServiceAPI.getListCoin(url);

        return list;

    }
 /**-------------------------------Cryptocoinchar API -------------------------------**/
/**-------------------------------Cryptonator API-------------------------------**/
    public double getCryptonator_PairBTC_USD(){
        String url=cryptonatorServiceAPI.getTicker_btc_usdURL();
        return  marketPrice.getMarketExchangeRate(url,0,null,"price");
    }
    public List<String> getListTicker_btc_usd(){
        String url=cryptonatorServiceAPI.getTicker_btc_usdURL();
        List<String> list;
        list=cryptonatorServiceAPI.getListTicker_btc_usd(url);
        return  list;
    }
    public List<String> getListCompleteTicker(){
        String url=cryptonatorServiceAPI.CompleteTickerURL;
        List<String> list;
        list=cryptonatorServiceAPI.getListCompleteTicker(url);
        return  list;
    }
/**-------------------------------Cryptonator API-------------------------------**/
}