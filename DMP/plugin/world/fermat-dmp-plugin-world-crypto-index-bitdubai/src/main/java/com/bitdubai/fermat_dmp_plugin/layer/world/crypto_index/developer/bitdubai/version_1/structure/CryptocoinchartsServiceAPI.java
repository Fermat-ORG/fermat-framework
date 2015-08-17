package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

/**
 * Created by francisco on 13/08/15.
 */
public class CryptocoinchartsServiceAPI {


    public String coin;
    public String tradingPair;
    public String listCoin;
    public String manyTradingPair;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }


    public String getTradingPair() {
        tradingPair="http://api.cryptocoincharts.info/tradingPair/";
        return tradingPair;
    }

    public String getListCoin() {
        listCoin="http://api.cryptocoincharts.info/listCoins";
        return listCoin;
    }

    public String getManyTradingPair() {
        manyTradingPair="http://api.cryptocoincharts.info/tradingPairs";
        return manyTradingPair;
    }



}