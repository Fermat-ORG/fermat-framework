package com.bitdubai.fermat_api.layer._1_definition.money;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

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

    public String getAddress (){
        return  this.address;

    }

    public CryptoCurrency getCryptoCurrency (){
        return  this.cryptoCurrency;

    }
    
}
