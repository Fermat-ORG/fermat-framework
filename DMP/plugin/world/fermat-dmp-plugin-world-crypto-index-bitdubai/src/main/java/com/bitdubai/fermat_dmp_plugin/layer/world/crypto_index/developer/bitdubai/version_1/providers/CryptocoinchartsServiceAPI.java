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
public class CryptocoinchartsServiceAPI {


    public String coin;
    public String tradingPair;

   public String getTradingPair(String c, String f)  {
        String pair = c + "_" + f;
        tradingPair="http://api.cryptocoincharts.info/tradingPair/"+ pair;
        return tradingPair;
    }
}