package com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.CantGetOutgoingIntraUserTransactionManagerException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserCantGetCryptoStatusException;

import java.util.UUID;

/**
 * Created by loui on 20/02/15.
 */
public interface OutgoingIntraUserManager {
    public IntraUserCryptoTransactionManager getTransactionManager() throws CantGetOutgoingIntraUserTransactionManagerException;
    public CryptoStatus getTransactionStatus(String transactionHash) throws OutgoingIntraUserCantGetCryptoStatusException;
}
