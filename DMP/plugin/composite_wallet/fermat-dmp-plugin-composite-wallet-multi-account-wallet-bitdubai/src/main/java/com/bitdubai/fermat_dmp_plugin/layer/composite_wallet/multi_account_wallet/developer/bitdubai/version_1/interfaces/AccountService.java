package com.bitdubai.fermat_dmp_plugin.layer.composite_wallet.multi_account_wallet.developer.bitdubai.version_1.interfaces;

import  com.bitdubai.fermat_dmp_plugin.layer.composite_wallet.multi_account_wallet.developer.bitdubai.version_1.exceptions.CantStartAccountServiceException;

/**
 * Created by ciencias on 3/26/15.
 */
public interface AccountService {

    public void start() throws CantStartAccountServiceException;

    public void stop();
    
}
