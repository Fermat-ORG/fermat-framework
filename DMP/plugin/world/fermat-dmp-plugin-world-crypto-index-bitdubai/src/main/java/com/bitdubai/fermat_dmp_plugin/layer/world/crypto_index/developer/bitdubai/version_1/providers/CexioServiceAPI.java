package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.InterfaceUrlAPI;

/**
 * Created by francisco on 02/09/15.
 */
public class CexioServiceAPI implements InterfaceUrlAPI {
    private String UrlAPI;
    @Override
    public String getUrlAPI(String c, String f) {
        UrlAPI="https://cex.io/api/ticker/"+c+"/"+f;
        return UrlAPI;
    }
}
