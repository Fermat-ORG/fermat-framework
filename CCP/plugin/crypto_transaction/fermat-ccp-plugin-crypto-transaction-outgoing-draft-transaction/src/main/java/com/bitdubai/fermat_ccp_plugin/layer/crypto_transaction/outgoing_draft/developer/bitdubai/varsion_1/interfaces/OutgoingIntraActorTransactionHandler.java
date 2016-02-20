package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.varsion_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.varsion_1.exceptions.OutgoingIntraActorCantHandleTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.varsion_1.util.OutgoingDraftTransactionWrapper;

/**
 * Created by eze on 2015.09.21..
 */
public interface OutgoingIntraActorTransactionHandler {
    public void handleTransaction(OutgoingDraftTransactionWrapper transaction, CryptoStatus cryptoStatus) throws OutgoingIntraActorCantHandleTransactionException;
}
