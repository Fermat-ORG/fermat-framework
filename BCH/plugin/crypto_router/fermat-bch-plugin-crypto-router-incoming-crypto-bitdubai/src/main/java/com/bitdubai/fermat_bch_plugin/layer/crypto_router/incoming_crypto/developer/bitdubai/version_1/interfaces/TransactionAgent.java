package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;

/**
 * Created by ciencias on 3/30/15.
 */
public interface TransactionAgent {
    
    void start() throws CantStartAgentException;

    void stop();
    
}
