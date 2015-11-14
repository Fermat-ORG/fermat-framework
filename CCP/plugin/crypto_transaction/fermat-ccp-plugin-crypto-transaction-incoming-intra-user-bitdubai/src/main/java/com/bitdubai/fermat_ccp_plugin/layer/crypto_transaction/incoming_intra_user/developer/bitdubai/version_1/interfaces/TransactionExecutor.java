package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantExecuteTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation;

/**
 * Created by eze on 2015.09.11..
 */
public interface TransactionExecutor {
    public void executeTransaction(TransactionCompleteInformation transactionCompleteInformation) throws IncomingIntraUserCantExecuteTransactionException;
}
