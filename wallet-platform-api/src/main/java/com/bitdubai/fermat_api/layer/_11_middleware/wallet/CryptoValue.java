package com.bitdubai.fermat_api.layer._11_middleware.wallet;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public interface CryptoValue {

    public Double getBalance() ;

    public CryptoCurrency getCryptoCurrency() ;
}
