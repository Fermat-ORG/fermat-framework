package com.bitdubai.fermat_dap_api.layer.dap_actor;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Nerio on 07/09/15.
 */
public interface DAPActorSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();
}
