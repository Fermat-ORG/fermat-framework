package com.bitdubai.fermat_api.layer.dmp_world;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 3/12/15.
 */
public interface CryptoWalletManager {
    
    public void createWallet (CryptoCurrency cryptoCurrency ) throws CantCreateCryptoWalletException;
    
    
}
