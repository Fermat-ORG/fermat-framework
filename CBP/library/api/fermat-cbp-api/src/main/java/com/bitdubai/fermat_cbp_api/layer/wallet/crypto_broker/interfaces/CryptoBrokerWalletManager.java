package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;

/**
 * Created by jorge on 26-10-2015.
 * Modified bu Franklin Marcano 30.11.2015
 */
public interface CryptoBrokerWalletManager {
    /**
     * This method create the wallet Crypto Broker
     * @param walletPublicKey
     * @return
     * @exception CantCreateCryptoBrokerWalletException
     */
    void createCryptoBrokerWallet(String walletPublicKey) throws com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
    /**
     * This method load the wallet Crypto Broker
     * @param walletPublicKey
     * @return CryptoBrokerWallet
     * @exception CryptoBrokerWalletNotFoundException
     */
    CryptoBrokerWallet loadCryptoBrokerWallet(String walletPublicKey) throws CryptoBrokerWalletNotFoundException;
}
