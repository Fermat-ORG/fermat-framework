package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

/**
 * Created by francisco on 31/08/15.
 */
public class CoinwarzServiceAPI {

    public String url;

    public String getUrlApi(){
        url="http://www.coinwarz.com/v1/api/coininformation/?apikey=9be66c1574604d28be459ea212c6eb1d&cointag=BTC";
        return url;
    }
}
