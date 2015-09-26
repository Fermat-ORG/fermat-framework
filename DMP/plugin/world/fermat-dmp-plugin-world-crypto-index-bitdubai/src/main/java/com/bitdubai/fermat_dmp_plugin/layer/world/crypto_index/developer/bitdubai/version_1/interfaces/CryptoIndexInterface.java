package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

import java.util.Date;

/**
 * Created by francisco on 10/09/15.
 */
public interface CryptoIndexInterface {
    /**
     * The method <code>getCryptyCurrency</code> gives us the name of the represented Crypty Currency
     * @return public CryptoCurrency
     */
    public String getCryptyCurrency();

    /**
     *The method <code>getFiatCurrency</code> gives us the name of the represented Fiat Currency
     * @return public FiatCurrency
     */
    public String getFiatCurrency();

    /**
     *The method <code>GetTime</code> gives us the name of the represented Date
     * @return  public String GetTime
     */
    public String getTime();

    /**
     *The method <code>priceMarket</code> gives us the name of the represented double
     * @return public double priceMarket
     */
    public double getRateExchange();

}
