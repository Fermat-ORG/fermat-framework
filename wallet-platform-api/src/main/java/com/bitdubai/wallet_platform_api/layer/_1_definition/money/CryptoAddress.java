package com.bitdubai.wallet_platform_api.layer._1_definition.money;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 02.02.15.
 */
public class CryptoAddress {

    CryptoCurrency cryptoCurrency;
    String address;
    
    public void setCryptoCurrency (CryptoCurrency cryptoCurrency){
        this.cryptoCurrency = cryptoCurrency;
    }
    
    public void setAddress (String address){
        this.address = address;
        //TODO: validate the format according to each cryptocurrency.
    }
    
}
