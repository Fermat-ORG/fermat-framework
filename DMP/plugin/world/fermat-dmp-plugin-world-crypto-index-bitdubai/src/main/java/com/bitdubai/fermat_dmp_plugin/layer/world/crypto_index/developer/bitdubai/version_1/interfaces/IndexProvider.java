package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;

/**
 * Created by francisco on 04/12/15.
 */
public interface IndexProvider {

    CryptoIndex getCurrentIndex(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency) throws CantGetIndexException;

}