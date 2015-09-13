package com.bitdubai.fermat_dap_api.layer.dap_middleware;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by franklin on 10/09/15.
 */
public interface DAPMiddlewareSubsystem {
    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
