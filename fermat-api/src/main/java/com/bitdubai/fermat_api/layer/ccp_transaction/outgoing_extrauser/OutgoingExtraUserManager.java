package com.bitdubai.fermat_api.layer.ccp_transaction.outgoing_extrauser;

import com.bitdubai.fermat_api.layer.ccp_transaction.outgoing_extrauser.exceptions.CantGetTransactionManagerException;

import java.util.UUID;

/**
 * Created by loui on 20/02/15.
 */
public interface OutgoingExtraUserManager {
    public TransactionManager getTransactionManager() throws CantGetTransactionManagerException;
}
