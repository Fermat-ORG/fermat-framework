package com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;

/**
 * Created by eze.postan.
 */
public interface OutgoingIntraActorManager {

    IntraActorCryptoTransactionManager getTransactionManager() throws CantGetOutgoingIntraActorTransactionManagerException;

    CryptoStatus getTransactionStatus(String transactionHash) throws OutgoingIntraActorCantGetCryptoStatusException;

}
