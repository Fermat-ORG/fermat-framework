package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PublicKey;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.JsonService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 13/08/15.
 */
public class CryptonatorServiceAPI {

    public String Ticker_btc_usdURL;
    public String CompleteTickerURL;
    public List<String> ListTicker_btc_usd;
    public List<String> ListCompleteTicker;
    public JSONObject jsonObject;
    JsonService jsonService = new JsonService();

    public String getCompleteTickerURL() {
        CompleteTickerURL="https://www.cryptonator.com/api/full/btc-usd";
        return CompleteTickerURL;
    }

    public String getTicker_btc_usdURL() {
        return Ticker_btc_usdURL="https://www.cryptonator.com/api/ticker/btc-usd";
    }
    public List<String> getListTicker_btc_usd(String url) {
        ListTicker_btc_usd= new ArrayList<>();
        jsonObject=jsonService.getJSONFromUrl(url);

        try {
            ListTicker_btc_usd.add((String) jsonObject.get("ticker"));
            ListTicker_btc_usd.add((String) jsonObject.get("base"));
            ListTicker_btc_usd.add((String) jsonObject.get("target"));
            ListTicker_btc_usd.add((String) jsonObject.get("price"));
            ListTicker_btc_usd.add((String) jsonObject.get("volume"));
            ListTicker_btc_usd.add((String) jsonObject.get("change"));
            ListTicker_btc_usd.add((String) jsonObject.get("timestamp"));
            ListTicker_btc_usd.add((String) jsonObject.get("success"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ListTicker_btc_usd;
    }
    public List<String> getListCompleteTicker(String url) {
        ListTicker_btc_usd= new ArrayList<>();
        jsonObject=jsonService.getJSONFromUrl(url);
       try {

            ListTicker_btc_usd.add((String) jsonObject.get("ticker"));
            ListTicker_btc_usd.add((String) jsonObject.get("base"));
            ListTicker_btc_usd.add((String) jsonObject.get("timestamp"));
            ListTicker_btc_usd.add((String) jsonObject.get("success"));
            ListTicker_btc_usd.add((String) jsonObject.get("error"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ListTicker_btc_usd;
    }


}
