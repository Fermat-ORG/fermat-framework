package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PublicKey;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.JsonService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private JSONObject jsonFather;
    private JSONObject jsonSon;

    JsonService jsonService = new JsonService();


    public String getCompleteTickerURL() {
        CompleteTickerURL="https://www.cryptonator.com/api/full/btc-usd";
        return CompleteTickerURL;
    }

    public String getTicker_btc_usdURL() {
        return Ticker_btc_usdURL="https://www.cryptonator.com/api/ticker/btc-usd";
    }
    public List<String> getListTicker_btc_usd(String url) throws IOException, JSONException {
        ListTicker_btc_usd= new ArrayList<>();
        jsonFather=jsonService.getJSONFromUrl(url);

        try {
            ListTicker_btc_usd.add((String) jsonFather.get("ticker"));
            ListTicker_btc_usd.add((String) jsonFather.get("timestamp"));
            ListTicker_btc_usd.add((String) jsonFather.get("success"));
            ListTicker_btc_usd.add((String) jsonFather.get("error"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ListTicker_btc_usd;
    }
    public List<String> getListTicker_btc_usdTicker(String url) throws IOException, JSONException {
        ListTicker_btc_usd= new ArrayList<>();
        jsonFather=jsonService.getJSONFromUrl(url);
        jsonSon=jsonFather.getJSONObject("ticker");

       try {

            ListTicker_btc_usd.add(jsonSon.getString("base"));
            ListTicker_btc_usd.add(jsonSon.getString("target"));
            ListTicker_btc_usd.add(jsonSon.getString("price"));
            ListTicker_btc_usd.add(jsonSon.getString("volume"));
            ListTicker_btc_usd.add(jsonSon.getString("change"));


       } catch (JSONException e) {
            e.printStackTrace();
        }

        return ListTicker_btc_usd;
    }


}
