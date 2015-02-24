package com.bitdubai.fermat_api.layer._7_world.crypto_index;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._7_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer._7_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;

/**
 * Created by ciencias on 2/14/15.
 */
public interface CryptoIndexManager {
    
    public double getCurrentMarketPrice (FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency) throws FiatCurrencyNotSupportedException,CryptoCurrencyNotSupportedException;
    
}
