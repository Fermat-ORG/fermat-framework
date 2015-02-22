package com.bitdubai.fermat_api.layer._6_world.crypto_index;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 2/14/15.
 */
public interface CryptoIndexManager {
    
    public double getCurrentMarketPrice (FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency) throws FiatCurrencyNotSupportedException,CryptoCurrencyNotSupportedException ;
    
}
