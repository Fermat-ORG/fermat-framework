package com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol;

import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 09/06/15.
 */
public interface TransactionSender <E> {

    public void confirmTransaction(UUID transactionID) throws CantConfirmTransactionException;
    public List<Transaction<E>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException;

}
