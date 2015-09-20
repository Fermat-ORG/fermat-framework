package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantStartAgentException;

/**
 * Created by ciencias on 3/30/15.
 */
public interface TransactionAgent {
    
    public void start () throws CantStartAgentException;

    public void stop();
    
}
