package com.bitdubai.fermat_api.layer.all_definition.money;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 02.02.15.
 */
public class CryptoAddress {

    CryptoCurrency cryptoCurrency;
    String address;

    public CryptoAddress(String address, CryptoCurrency cryptoCurrency) {
        this.address = address;
        this.cryptoCurrency = cryptoCurrency;
    }

    public CryptoAddress() {
    }

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
