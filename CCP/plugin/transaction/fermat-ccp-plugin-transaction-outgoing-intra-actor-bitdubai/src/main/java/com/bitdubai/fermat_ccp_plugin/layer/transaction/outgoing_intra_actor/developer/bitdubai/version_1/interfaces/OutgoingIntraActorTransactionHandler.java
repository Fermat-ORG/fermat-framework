package com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantHandleTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionWrapper;

/**
 * Created by eze on 2015.09.21..
 */
public interface OutgoingIntraActorTransactionHandler {
    public void handleTransaction(OutgoingIntraActorTransactionWrapper transaction, CryptoStatus cryptoStatus) throws OutgoingIntraActorCantHandleTransactionException;
}
