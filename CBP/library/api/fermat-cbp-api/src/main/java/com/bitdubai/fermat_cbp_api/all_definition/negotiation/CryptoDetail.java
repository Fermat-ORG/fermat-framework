package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CryptoCurrencyType;

/**
 * Created by jorge on 10-10-2015.
 */
public interface CryptoDetail {
    CryptoCurrencyType getCurrency();
    String getAddress();
}
