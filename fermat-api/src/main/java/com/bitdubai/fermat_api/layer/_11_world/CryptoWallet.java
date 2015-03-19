package com.bitdubai.fermat_api.layer._11_world;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Created by ciencias on 3/12/15.
 */
public interface CryptoWallet {

    
    public void start();
    
    public void stop();
    
    public long getWalletBalance(CryptoCurrency cryptoCurrency,UUID walletId);
    
    public long getAddressBalance(CryptoAddress cryptoAddress);
    
    public void sendCrypto (UUID walletId,CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddress);
    
    
}
