package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * Created by loui on 20/02/15.
 */
public interface OutgoingExtraUserManager extends FermatManager {
    public TransactionManager getTransactionManager() throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.CantGetTransactionManagerException;
}
