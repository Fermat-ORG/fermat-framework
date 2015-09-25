package com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.InterfaceUrlAPI;

/**
 * Created by francisco on 02/09/15.
 */
public class CcexServiceAPI implements InterfaceUrlAPI {
    private String UrlAPI;
    @Override
    public String getUrlAPI(String c, String f) {
        String pair = c.toLowerCase() + "-" + f.toLowerCase();
        UrlAPI="https://c-cex.com/t/"+pair+".json";
        return UrlAPI;
    }
}
