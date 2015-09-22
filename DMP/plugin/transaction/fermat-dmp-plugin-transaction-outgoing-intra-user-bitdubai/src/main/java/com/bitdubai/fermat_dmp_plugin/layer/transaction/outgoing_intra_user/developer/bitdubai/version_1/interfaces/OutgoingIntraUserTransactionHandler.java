package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.util.OutgoingIntraUserTransactionWrapper;

/**
 * Created by eze on 2015.09.21..
 */
public interface OutgoingIntraUserTransactionHandler {
    public void handleTransaction(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus cryptoStatus);
}
