package com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.InterfaceUrlAPI;

/**
 * Created by francisco on 02/09/15.
 */
public class BtceServiceAPI implements InterfaceUrlAPI {
    private String UrlAPI;

    @Override
    public String getUrlAPI(String c, String f) {
        String pair = c.toLowerCase() + "_" + f.toLowerCase();
        UrlAPI="https://btc-e.com/api/3/ticker/"+ pair;
        return UrlAPI;
    }
}
