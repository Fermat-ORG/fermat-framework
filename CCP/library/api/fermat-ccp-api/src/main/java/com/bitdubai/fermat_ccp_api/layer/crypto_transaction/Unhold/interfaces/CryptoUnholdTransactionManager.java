package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces;

import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;

import java.util.UUID;

/**
 * Created by Franklin Marcano on 21/11/2015.
 */
public interface CryptoUnholdTransactionManager {

    CryptoHoldTransaction   createCryptoUnholdTransaction(CryptoUnholdTransactionParameters UnholdParameters) throws CantCreateHoldTransactionException;
    CryptoTransactionStatus getCryptoUnholdTransactionStatus(UUID transactionId) throws CantGetHoldTransactionException;
}
