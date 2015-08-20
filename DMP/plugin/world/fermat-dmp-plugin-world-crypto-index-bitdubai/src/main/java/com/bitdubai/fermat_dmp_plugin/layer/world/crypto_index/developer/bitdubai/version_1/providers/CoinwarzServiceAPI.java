package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

/**
 * Created by francisco on 13/08/15.
 */
public class CoinwarzServiceAPI {

    public String ApiKey;
    public String APIKeyInformationURL;
    public String MiningProfitabilityURL;


    public String CoinInformationURL;

/**
 * The names of the methods are formed depending on the API documentation
 * **/

    public String getApiKey() {
        return ApiKey;
    }

    public void setApiKey(String apiKey) {
        ApiKey = apiKey;
    }
    public String APIKeyInformationURL(String ApiKey) {
        APIKeyInformationURL="http://www.coinwarz.c om/v1/api/apikeyinfo?apikey="+ApiKey;
        return APIKeyInformationURL;
    }

    public String MiningProfitabilityURL(String ApiKey) {
        MiningProfitabilityURL="http://www.coinwarz.com/v1/api/profitability/?apikey="+ApiKey+"&algo=all";
        return MiningProfitabilityURL;
    }
    public String getCoinInformationURL(String ApiKey) {
        CoinInformationURL="http://www.coinwarz.com/v1/api/coininformation/?apikey="+ApiKey+"&cointag=BTC";
        return CoinInformationURL;
    }



}
