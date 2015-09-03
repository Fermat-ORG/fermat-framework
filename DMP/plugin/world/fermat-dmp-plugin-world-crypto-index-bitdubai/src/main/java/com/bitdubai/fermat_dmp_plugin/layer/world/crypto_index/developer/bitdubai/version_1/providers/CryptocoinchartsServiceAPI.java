package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.InterfaceUrlAPI;

/**
 * Created by francisco on 13/08/15.
 */
public class CryptocoinchartsServiceAPI implements InterfaceUrlAPI {

    public String UrlAPI;

    @Override
   public String getUrlAPI(String c, String f)  {
        String pair = c + "_" + f;
        UrlAPI="http://api.cryptocoincharts.info/tradingPair/"+ pair;
        return UrlAPI;
    }
}