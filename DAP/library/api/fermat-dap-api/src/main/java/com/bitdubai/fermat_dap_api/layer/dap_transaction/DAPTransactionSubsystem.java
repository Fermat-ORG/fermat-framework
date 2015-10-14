package com.bitdubai.fermat_dap_api.layer.dap_transaction;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/09/15.
 */
public interface DAPTransactionSubsystem {
    void start () throws CantStartSubsystemException;
    Plugin getPlugin();
}
