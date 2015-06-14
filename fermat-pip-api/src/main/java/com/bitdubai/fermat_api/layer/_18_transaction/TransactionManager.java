package com.bitdubai.fermat_api.layer._18_transaction;

import com.bitdubai.fermat_api.layer._18_transaction.exceptions.CantReleaseTransactionException;
import com.bitdubai.fermat_api.layer._18_transaction.exceptions.CantSearchForTransactionsException;

import java.util.UUID;

/**
 * Created by Arturo Vallone on 05/05/15.
 */
public interface TransactionManager {
    public UUID getNextPendingTransactionByDestination (UUID destinationId) throws CantSearchForTransactionsException;
    public boolean releaseTransaction (UUID trxID) throws CantReleaseTransactionException;
}
