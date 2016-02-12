package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * Created by eze on 2015.06.18..
 */
public interface TransactionSender<E> extends FermatManager {
    public TransactionProtocolManager<E> getTransactionManager();
}
