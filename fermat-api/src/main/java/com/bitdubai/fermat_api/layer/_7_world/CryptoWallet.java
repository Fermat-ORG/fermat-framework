package com.bitdubai.fermat_api.layer._7_world;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;

/**
 * Created by ciencias on 3/12/15.
 */
public interface CryptoWallet {

    public double getWalletBalance();
    
    public double getAddressBalance(CryptoAddress cryptoAddress);
    
    public void sendCrypto (CryptoCurrency cryptoCurrency, Double amount, CryptoAddress cryptoAddress);
    
    
}
