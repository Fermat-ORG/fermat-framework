package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.interfaces;

/**
 * Created by ciencias on 3/30/15.
 */
public interface TransactionAgent {
    
    public void start () throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantStartAgentException;

    public void stop();
    
}
