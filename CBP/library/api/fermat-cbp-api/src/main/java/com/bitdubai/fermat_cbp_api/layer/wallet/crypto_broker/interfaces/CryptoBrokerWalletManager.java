package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoBrokerWalletManager {
    CryptoBrokerWallet createNewCryptoBrokerWallet(String cryptoBroker) throws com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
    CryptoBrokerWallet getCryptoBrokerWallet(String cryptoBroker) throws CryptoBrokerWalletNotFoundException;
}
