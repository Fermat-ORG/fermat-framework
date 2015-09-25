package com.bitdubai.fermat_api.layer.ccp_transaction.outgoing_intrauser.interfaces;

import com.bitdubai.fermat_api.layer.ccp_transaction.outgoing_intrauser.exceptions.CantGetOutgoingIntraUserTransactionManagerException;

/**
 * Created by loui on 20/02/15.
 */
public interface OutgoingIntraUserManager {
    public OutgoingIntraUserTransactionManager getTransactionManager() throws CantGetOutgoingIntraUserTransactionManagerException;
}
