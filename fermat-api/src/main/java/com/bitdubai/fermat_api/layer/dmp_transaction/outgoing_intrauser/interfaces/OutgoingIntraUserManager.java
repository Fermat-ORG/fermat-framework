package com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces;

import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.CantGetOutgoingIntraUserTransactionManagerException;

/**
 * Created by loui on 20/02/15.
 */
public interface OutgoingIntraUserManager {
    public IntraUserCryptoTransactionManager getTransactionManager() throws CantGetOutgoingIntraUserTransactionManagerException;
}
