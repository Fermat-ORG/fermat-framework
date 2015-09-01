package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

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

    public String CompleteTickerURL;


    public String getCompleteTickerURL() {
        CompleteTickerURL="https://www.cryptonator.com/api/full/btc-usd";
        return CompleteTickerURL;
    }



}
