package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;

/**
 * Created by eze.postan.
 */
public interface OutgoingIntraActorManager extends FermatManager {

    IntraActorCryptoTransactionManager getTransactionManager() throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;

    CryptoStatus getTransactionStatus(String transactionHash) throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;

}
