package com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.exceptions.CantStartAccountServiceException;

/**
 * Created by ciencias on 3/26/15.
 */
public interface AccountService {

    public void start() throws CantStartAccountServiceException;

    public void stop();
    
}
