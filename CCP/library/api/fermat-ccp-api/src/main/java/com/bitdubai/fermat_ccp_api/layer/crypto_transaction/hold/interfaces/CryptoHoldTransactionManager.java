package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces;

import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantGetHoldTransactionException;

import java.util.UUID;

/**
 * Created by Franklin Marcano on 21/11/2015.
 */
public interface CryptoHoldTransactionManager {

    CryptoHoldTransaction   createCryptoHoldTransaction(CryptoHoldTransactionParameters holdParameters) throws CantCreateHoldTransactionException;
    CryptoTransactionStatus getCryptoHoldTransactionStatus(UUID transactionId) throws CantGetHoldTransactionException;
}
