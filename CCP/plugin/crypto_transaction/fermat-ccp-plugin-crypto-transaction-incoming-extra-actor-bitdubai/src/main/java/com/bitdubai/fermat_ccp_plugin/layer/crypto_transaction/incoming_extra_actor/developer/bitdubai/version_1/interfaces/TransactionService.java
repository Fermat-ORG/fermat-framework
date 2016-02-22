package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantStartServiceException;

/**
 * Created by ciencias on 3/30/15.
 */
public interface TransactionService {

    void start() throws CantStartServiceException;

    void stop();
    
    ServiceStatus getStatus();
}
