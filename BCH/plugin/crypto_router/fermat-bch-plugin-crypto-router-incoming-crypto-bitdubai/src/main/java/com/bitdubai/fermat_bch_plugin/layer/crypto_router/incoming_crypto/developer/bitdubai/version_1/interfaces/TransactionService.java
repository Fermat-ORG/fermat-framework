package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;

/**
 * Created by ciencias on 3/30/15.
 */
public interface TransactionService {

    void start() throws CantStartServiceException;

    void stop();
    
    ServiceStatus getStatus();
}
