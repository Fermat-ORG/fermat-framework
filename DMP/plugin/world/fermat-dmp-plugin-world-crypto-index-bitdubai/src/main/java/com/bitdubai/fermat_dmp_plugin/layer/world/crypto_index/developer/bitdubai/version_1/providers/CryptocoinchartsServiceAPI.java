package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.JsonService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 13/08/15.
 */
public class CryptocoinchartsServiceAPI {


    public String coin;
    public String tradingPair;
    public String listCoin;
    public String manyTradingPair;
    public JSONObject jsonObject;
    public List<String > jsonList;
    JsonService jsonService = new JsonService();

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }


    public String getTradingPair(String c, String f)  {

        String pair = c + "_" + f;
        tradingPair="http://api.cryptocoincharts.info/tradingPair/"+ pair;
        return tradingPair;
    }

    public String getListCoinURL() {
        listCoin="http://api.cryptocoincharts.info/listCoins/";
        return listCoin;
    }

    public String getManyTradingPair() {
        manyTradingPair="http://api.cryptocoincharts.info/tradingPairs/";
        return manyTradingPair;
    }

    /** Return Many TradingPair from Cryptocoinchar **/
    public List<String> getListTradingPair(String url) throws IOException, JSONException {
        jsonList = new ArrayList<>();
        jsonObject=jsonService.getJSONFromUrl(url);
        try {
            jsonList.add((String) jsonObject.get("id"));
            jsonList.add((String) jsonObject.get("price"));
            jsonList.add((String) jsonObject.get("price_before_24h"));
            jsonList.add((String) jsonObject.get("volume_first"));
            jsonList.add((String) jsonObject.get("volume_second"));
            jsonList.add((String) jsonObject.get("volume_btc"));
            jsonList.add((String) jsonObject.get("best_market"));
            jsonList.add((String) jsonObject.get("latest_trade"));
            jsonList.add((String) jsonObject.get("coin1"));
            jsonList.add((String) jsonObject.get("coin2"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonList;
    }
    /* list all coins with their data which are available on cryptocoincharts.*/
    public List<String> getListCoin(String url) throws IOException, JSONException {
        jsonList = new ArrayList<>();
        jsonObject=jsonService.getJSONFromUrl(url);
        try {
            jsonList.add((String) jsonObject.get("id"));
            jsonList.add((String) jsonObject.get("name"));
            jsonList.add((String) jsonObject.get("website"));
            jsonList.add((String) jsonObject.get("price_btc"));
            jsonList.add((String) jsonObject.get("volume_btc"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonList;
    }


}