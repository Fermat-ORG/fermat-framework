package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 09/06/15.
 */
public interface TransactionProtocolManager<E> extends FermatManager {
    void confirmReception(UUID transactionID) throws CantConfirmTransactionException;

    List<Transaction<E>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException;

}
