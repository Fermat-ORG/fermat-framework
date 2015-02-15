package com.bitdubai.wallet_platform_api.layer._10_middleware.wallet;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public interface CryptoValue {

    public Double getBalance() ;

    public CryptoCurrency getCryptoCurrency() ;
}
