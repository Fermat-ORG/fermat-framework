package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces;

import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.exceptions.CantCreateUnHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.exceptions.CantGetUnHoldTransactionException;


import java.util.UUID;

/**
 * Created by Franklin Marcano on 21/11/2015.
 */
public interface CryptoUnholdTransactionManager {

    CryptoUnholdTransaction   createCryptoUnholdTransaction(CryptoUnholdTransactionParameters UnholdParameters) throws CantCreateUnHoldTransactionException;
    CryptoTransactionStatus getCryptoUnholdTransactionStatus(UUID transactionId) throws CantGetUnHoldTransactionException;
}
