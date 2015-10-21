package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantLoadCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantRegisterCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantTransactionCryptoBrokerException;

import java.util.List;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface CryptoBrokerManager {

    CryptoBroker loadCryptoBrokerWallet(String walletPublicKey) throws CantLoadCryptoBrokerException;

    void createCryptoBrokerWallet(String walletPublicKey) throws CantCreateCryptoBrokerException;
}