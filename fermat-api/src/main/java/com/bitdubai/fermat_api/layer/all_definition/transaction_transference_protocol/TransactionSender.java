package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

/**
 * Created by eze on 2015.06.18..
 */
public interface TransactionSender<E> {
    public TransactionProtocolManager<E> getTransactionManager();
}
