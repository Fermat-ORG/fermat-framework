package com.bitdubai.fermat_api.layer.dmp_world.crypto_index;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;

/**
 * Created by ciencias on 2/14/15.
 */
public interface CryptoIndexManager {

    double getMarketPrice(FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException;


}
