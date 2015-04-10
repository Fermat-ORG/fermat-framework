package com.bitdubai.fermat_api.layer._11_world;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

import java.util.UUID;

/**
 * Created by ciencias on 3/12/15.
 */
public interface CryptoWalletManager {
    
    public void createWallet (CryptoCurrency cryptoCurrency ) throws CantCreateCryptoWalletException;
    
    
}
