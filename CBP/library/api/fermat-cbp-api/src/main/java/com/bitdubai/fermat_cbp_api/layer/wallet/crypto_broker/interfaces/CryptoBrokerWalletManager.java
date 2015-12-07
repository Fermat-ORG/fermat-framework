package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;

/**
 * Created by jorge on 26-10-2015.
 * Modified bu Franklin Marcano 30.11.2015
 */
public interface CryptoBrokerWalletManager {
    void createCryptoBrokerWallet(String walletPublicKey) throws com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
    CryptoBrokerWallet loadCryptoBrokerWallet(String walletPublicKey) throws CryptoBrokerWalletNotFoundException;
}
