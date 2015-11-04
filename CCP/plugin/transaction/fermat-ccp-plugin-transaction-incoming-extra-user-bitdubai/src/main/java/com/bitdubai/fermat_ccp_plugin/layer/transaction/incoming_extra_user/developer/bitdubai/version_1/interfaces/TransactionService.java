package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantStartServiceException;

/**
 * Created by ciencias on 3/30/15.
 */
public interface TransactionService {

    public void start () throws CantStartServiceException;

    public void stop();
    
    public ServiceStatus getStatus ();
}
