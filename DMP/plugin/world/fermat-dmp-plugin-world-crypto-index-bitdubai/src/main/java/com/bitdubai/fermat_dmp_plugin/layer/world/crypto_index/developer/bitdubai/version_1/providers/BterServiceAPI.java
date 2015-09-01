package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

/**
 * Created by francisco on 31/08/15.
 */
public class BterServiceAPI {

    private String url;
    public String getUrlApi(String crypto, String fiat){

        url = "http://data.bter.com/api/1/ticker/"+crypto+"_"+fiat;
        return url;
    }
}
