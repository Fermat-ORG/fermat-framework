package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexInterface;

/**
 * Created by francisco on 11/09/15.
 * TODO add a little explanation of the class
 */
public class CryptoIndexList implements CryptoIndexInterface {

    private String cryptoCurrency;
    private String fiatCurrency;
    private String time;
    private double rateExchange;

    /**
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param time
     * @param rateExchange
     */
    public CryptoIndexList(String cryptoCurrency, String fiatCurrency, String time, double rateExchange) {
        this.cryptoCurrency = cryptoCurrency;
        this.fiatCurrency = fiatCurrency;
        this.time = time;
        this.rateExchange = rateExchange;
    }

    @Override
    public String getCryptyCurrency() {
        return cryptoCurrency;
    }

    @Override
    public String getFiatCurrency() {
        return fiatCurrency;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public double getRateExchange() {
        return rateExchange;
    }
}
