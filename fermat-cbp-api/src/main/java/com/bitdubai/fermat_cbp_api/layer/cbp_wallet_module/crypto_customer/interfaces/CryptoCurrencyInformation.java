package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

/**
 * Created by nelson on 22/09/15.
 */
public interface CryptoCurrencyInformation {

    /**
     * @return the Crypto Currency
     */
    CryptoCurrency getCryptoCurrency();

    /**
     * @return the amount of Crypto Currency
     */
    float getAmmout();
}
