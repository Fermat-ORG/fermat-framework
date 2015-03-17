package com.bitdubai.fermat_api.layer._7_world;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._8_crypto_network.*;

import java.util.UUID;

/**
 * Created by ciencias on 3/12/15.
 */
public interface CryptoWalletManager {
    
    public void createWallet (CryptoCurrency cryptoCurrency,UUID walletId ) throws CantCreateCryptoWalletException;
    
    
}
