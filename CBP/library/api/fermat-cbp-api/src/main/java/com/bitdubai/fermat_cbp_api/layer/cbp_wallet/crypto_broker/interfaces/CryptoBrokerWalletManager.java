package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoBrokerWalletManager {
    CryptoBrokerWallet createNewCryptoBrokerWallet(ActorIdentity cryptoBroker) throws CantCreateCryptoBrokerWalletException;
    CryptoBrokerWallet getCryptoBrokerWallet(ActorIdentity cryptoBroker) throws CryptoBrokerWalletNotFoundException;
}
