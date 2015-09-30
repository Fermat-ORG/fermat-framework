package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionType;

/**
 * Created by jorge on 29-09-2015.
 */
public interface CryptoBrokerWallet {

    String getPublicKeyBroker();

    String getPublicKeyWallet();

    BusinessTransactionType getTransactionType();

}
