package com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;

/**
 * Created by ciencias on 3/30/15.
 */
public interface TransactionService {

    public void start () throws CantStartServiceException;

    public void stop();
    
    public ServiceStatus getStatus ();
}
