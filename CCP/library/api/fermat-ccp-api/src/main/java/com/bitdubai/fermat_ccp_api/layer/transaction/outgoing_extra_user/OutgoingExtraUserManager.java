package com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_extra_user;

/**
 * Created by loui on 20/02/15.
 */
public interface OutgoingExtraUserManager {
    public TransactionManager getTransactionManager() throws com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_extra_user.exceptions.CantGetTransactionManagerException;
}
