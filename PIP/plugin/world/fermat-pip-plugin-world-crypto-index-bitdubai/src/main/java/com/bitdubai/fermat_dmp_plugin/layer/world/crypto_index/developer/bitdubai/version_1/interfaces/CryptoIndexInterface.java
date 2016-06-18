package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces;

/**
 * Created by francisco on 10/09/15.
 */
public interface CryptoIndexInterface {
    /**
     * The method <code>getCryptyCurrency</code> gives us the name of the represented Crypty Currency
     *
     * @return public CryptoCurrency
     */
    String getCryptyCurrency();

    /**
     * The method <code>getFiatCurrency</code> gives us the name of the represented Fiat Currency
     *
     * @return public FiatCurrency
     */
    String getFiatCurrency();

    /**
     * The method <code>GetTime</code> gives us the name of the represented Date
     *
     * @return public String GetTime
     */
    String getTime();

    /**
     * The method <code>priceMarket</code> gives us the name of the represented double
     *
     * @return public double priceMarket
     */
    double getRateExchange();

}
