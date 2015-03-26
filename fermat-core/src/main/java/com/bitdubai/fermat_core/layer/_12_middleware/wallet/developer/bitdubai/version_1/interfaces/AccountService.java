package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.exceptions.CantStartAccountException;

/**
 * Created by ciencias on 3/26/15.
 */
public interface AccountService {

    public void start() throws CantStartAccountException;

    public void stop();
    
}
