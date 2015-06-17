package com.bitdubai.fermat_p2p_api.layer.cry_crypto_network;

import com.bitdubai.fermat_p2p_api.layer.all_definition.money.CryptoAddress;

/**
 * Created by ciencias on 23.01.15.
 */
public interface CryptoWallet {

    
    public void sendToAddress (CryptoAddress address, double amount);
    
    
}
