package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.MarketRateProvider;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by francisco on 12/08/15.
 */
public class MarketPrice implements MarketRateProvider {

    FiatCurrency   f;
    CryptoCurrency c;
    Double marketExchangeRate = null;
    String pair;
    JsonService jsonService = new JsonService();
    JSONObject jsonObject = new JSONObject();


    public MarketPrice(FiatCurrency f, CryptoCurrency c, long time) {
        this.f = f;
        this.c = c;
        this.time = time;
    }



    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public CryptoCurrency getC() {
        return c;
    }

    public void setC(CryptoCurrency c) {
        this.c = c;
    }

    public FiatCurrency getF() {
        return f;
    }

    public void setF(FiatCurrency f) {
        this.f = f;
    }

    long time;

    /**
     *returns exchange between pairs Crypto-Fiat or Fiat-Crypto
     * **/
    @Override
    public double getMarketExchangeRate(String pair, long time, String url, String jsonField) {

   try {
            marketExchangeRate= (Double) jsonInterface(url+pair).get(jsonField);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return marketExchangeRate;
    }

    @Override
    public JSONObject jsonInterface(String url) {
        jsonObject=jsonService.getJSONFromUrl(url);
        return  jsonObject;
    }
    /**Return Pair Crypto Fiat**/
    @Override
    public String pairCryptoFiat(CryptoCurrency c, FiatCurrency f) {
        pair=c.getCode() + "_" + f.getCode();
        return pair;
    }
    /**Return Pair Fiat Crypto**/
    @Override
    public String pairFiatCrypto(CryptoCurrency c, FiatCurrency f) {
        pair=f.getCode() + "_" + c.getCode();
        return pair;
    }
    /**Return Pair Crypto Crypto**/
    @Override
    public String pairCryptoCrypto(CryptoCurrency c1, CryptoCurrency c2) {
        pair=c1.getCode() + "_" + c2.getCode();
        return pair;
    }
    /**Return Pair Fiat Fiat**/
    @Override
    public String pairFiatFiat(FiatCurrency f1, FiatCurrency f2) {
        pair=f1.getCode() + "_" + f2.getCode();
        return pair;
    }


}