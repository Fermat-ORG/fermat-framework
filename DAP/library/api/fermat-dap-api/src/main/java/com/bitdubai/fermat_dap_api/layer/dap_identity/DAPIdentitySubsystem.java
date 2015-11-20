package com.bitdubai.fermat_dap_api.layer.dap_identity;

import com.bitdubai.fermat_api.Plugin;


/**
 * Created by Nerio on 07/09/15.
 */
public interface DAPIdentitySubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();
}
